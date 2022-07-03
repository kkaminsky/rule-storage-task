package zone.bi.triplan.rule.storage

/**
 * Движок правил.
 */
fun interface RuleEngine {

    /**
     * Проверяет событие набором правил. Возвращает первое сработавшее правило. Если ни одно правило не сработало –
     * возвращает null.
     */
    fun check(event: Event): Rule?

    /**
     * Движок правил.
     */
    companion object {

        /**
         * Фабричный метод для движка правил.
         */
        operator fun invoke(rules: Iterable<Rule>): RuleEngine =
            RuleEngine { event -> rules.find { rule -> rule(event) } }
    }
}