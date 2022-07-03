package zone.bi.triplan.rule.storage.impl

import zone.bi.triplan.rule.storage.PriorityRule
import zone.bi.triplan.rule.storage.Rule
import zone.bi.triplan.rule.storage.Storage
import java.util.concurrent.CopyOnWriteArrayList

class CopyOnWriteStorage : Storage<PriorityRule> {

    private val innerBuffer: CopyOnWriteArrayList<PriorityRule> = CopyOnWriteArrayList()

    override fun iterator(): Iterator<Rule> = this.innerBuffer.map(PriorityRule::rule).iterator()

    override fun add(element: PriorityRule) {
        this.innerBuffer.add(element).let { if (it) this.innerBuffer.sort() }
    }

    override fun remove(ruleId: Int) {
        this.innerBuffer.removeIf { rule -> rule.rule.id == ruleId }
    }
}

