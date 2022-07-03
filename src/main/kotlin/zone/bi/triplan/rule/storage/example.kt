package zone.bi.triplan.rule.storage

val EXAMPLE_EVENT = mapOf<String, Any?>()

fun main() {

    val n = 10
    Example(SimpleStorage()) { rule -> PriorityRule(n - rule.id, rule) }
        .example()

}

/**
 * Пример запуска. Необходимо реализовать хранилище и функцию, создающую обертку для приоритизации правила.
 */
class Example<T>(private val storage: Storage<T>, private val generator: (Rule) -> T) {

    /**
     * Записывает в хранилище 3 правила и выводит на экран сработавшее правило.
     */
    fun example() {
        fun generateRule(index: Int): Rule = Rule(index) { true }

        (1..3).forEach {
            this.storage.add(
                this.generator(generateRule(it))
            )
        }

        val ruleEngine = RuleEngine(this.storage)

        val triggeredRule = ruleEngine.check(EXAMPLE_EVENT)
        println("Triggered rule - ${triggeredRule ?: "none"}")
    }
}

/**
 * Приоритезированное правило.
 */
data class PriorityRule(
    /**
     * Числовой приоритет. Чем ниже, тем более приоритетно правило.
     */
    val priority: Int,
    /**
     * Правило.
     * @see Rule
     */
    val rule: Rule,
) : Comparable<PriorityRule> {

    override fun compareTo(other: PriorityRule): Int =
        Comparator.comparingInt(PriorityRule::priority).compare(this, other)
}

/**
 * Просто хранилище на основе изменяемого списка.
 */
class SimpleStorage : Storage<PriorityRule> {

    private val innerBuffer: MutableList<PriorityRule> = arrayListOf()

    override fun iterator(): Iterator<Rule> = this.innerBuffer.sorted().map(PriorityRule::rule).iterator()

    override fun add(element: PriorityRule) {
        this.innerBuffer.add(element)
    }

    override fun remove(ruleId: Int) {
        this.innerBuffer.removeIf { rule -> rule.rule.id == ruleId }
    }

    override fun toString(): String {
        return this.toList().toString()
    }
}
