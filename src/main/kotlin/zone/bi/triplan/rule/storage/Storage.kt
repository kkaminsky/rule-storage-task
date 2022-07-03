package zone.bi.triplan.rule.storage


/**
 * Хранилище правил.
 *
 * Хранит правила в отсортированном виде, согласно их приоритету.
 *
 * @param T тип обертки над правилом, которая отвечает за приоритизацию.
 */
interface Storage<T> : Iterable<Rule> {

    /**
     * Добавляет элемент, содержащий правило и его приоритет.
     *
     * @param element обертка надо правилом, которая отвечает за его приоритизацию.
     */
    fun add(element: T)

    /**
     * Удаляет правило из хранилища по [Rule.id].
     * @param ruleId идентификатор правила.
     */
    fun remove(ruleId: Int)
}

/**
 * Событие, которое мы должны проверить набором правил.
 */
typealias Event = Map<String, Any?>

/**
 * Правило.
 */
data class Rule(
    /**
     * Идентификатор правила.
     */
    val id: Int,
    /**
     * Условие правила
     */
    val condition: (Event) -> Boolean,
) {

    override fun toString(): String {
        return "Rule(id=$id)"
    }

    operator fun invoke(event: Event) : Boolean = this.condition(event)
}