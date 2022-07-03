package zone.bi.triplan.rule.storage.impl

import zone.bi.triplan.rule.storage.PriorityRule
import zone.bi.triplan.rule.storage.Rule
import zone.bi.triplan.rule.storage.Storage
import java.util.concurrent.ConcurrentSkipListSet

class SkipListStorage : Storage<PriorityRule> {

    private val innerBuffer = ConcurrentSkipListSet<PriorityRule>()

    override fun add(element: PriorityRule) {
        this.innerBuffer.add(element)
    }

    override fun iterator(): Iterator<Rule> {
        return this.innerBuffer.map(PriorityRule::rule).iterator()
    }

    override fun remove(ruleId: Int) {
        //TODO: change signature from Int to T?
    }
}