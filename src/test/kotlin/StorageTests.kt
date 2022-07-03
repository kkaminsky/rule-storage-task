import org.junit.jupiter.api.Test
import zone.bi.triplan.rule.storage.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import kotlin.random.Random
import kotlin.test.assertEquals


const val EXECUTORS_COUNT = 1000

abstract class StorageTests(private val storage: Storage<PriorityRule>) {


    private val ruleEngine = RuleEngine(this.storage)


    @Test
    fun testConsistent() {
        val tasksCount = 10_000

        val executor = Executors.newFixedThreadPool(EXECUTORS_COUNT)

        val latch = CountDownLatch(tasksCount)

        (1..tasksCount).forEach {
            executor.submit {
                addRule(it)
                latch.countDown()
            }
        }
        latch.await()

        assertEquals(tasksCount, storage.count())
    }

    @Test
    fun testPerformance() {
        val taskCount = 100_000

        val executors = Executors.newFixedThreadPool(EXECUTORS_COUNT)

        val latch = CountDownLatch(taskCount)

        (1..taskCount).forEach {
            fun task() = if (it % 100 == 0) addRule(it) else checkEvent()
            executors.submit {
                task()
                latch.countDown()
            }
        }
        latch.await()
    }

    private fun addRule(ruleId: Int) {
        val priority = Int.MAX_VALUE - ruleId //должно быть уникальным
        storage.add(
            PriorityRule(
                priority = priority,
                rule = Rule(
                    id = ruleId,
                    condition = { true }
                )
            )
        )
        println("Added - $ruleId , priority - $priority")

    }

    private fun checkEvent() {
        val triggeredRule = ruleEngine.check(EXAMPLE_EVENT)
        println("Triggered rule - ${triggeredRule ?: "none"}")
    }
}