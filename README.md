# Задание

Проверяет знания структур и алгоритмов.

Сервис проверяет некоторое событие набором правил и возвращает первое сработавшее правило.

Необходимо разработать способ сортировки правил и свою реализацию хранилища правил.

## Пример

В данном задании у нас есть возможность влиять на структуру правила – мы можем добавлять туда 
любые поля и методы через класс-обертку. В качестве примера мы используем обертку, где приоритет
правила относительно других задается числом: чем меньше число, тем выше приоритет:

```kotlin
data class PriorityRule(val priority : Int, val rule : Rule) : Comparable<PriorityRule> {

    override fun compareTo(other: PriorityRule): Int =
        Comparator.comparingInt(PriorityRule::priority).compare(this, other)
}
```

А в качестве хранилища используем простую обертку над java.util.ArrayList:

```kotlin
class SimpleStorage : Storage<PriorityRule> {

    private val innerBuffer: MutableList<PriorityRule> = arrayListOf()

    override fun iterator(): Iterator<Rule> = this.innerBuffer.sorted().map(PriorityRule::rule).iterator()

    override fun add(element: PriorityRule) {
        this.innerBuffer.add(element)
    }

    override fun remove(ruleId: Int) {
        this.innerBuffer.removeIf { rule -> rule.rule.id == ruleId }
    }
}
```
