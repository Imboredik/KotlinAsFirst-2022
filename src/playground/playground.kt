package playground

import kotlinx.html.MAP
import lesson5.task1.containsIn
import ru.spbstu.kotlin.typeclass.classes.ListFunctor.fmap
import java.io.File
import java.io.IOException


fun myFun(text: String, teams: List<String>): Collection<Any> {
    val fin = mutableListOf<String>()
    val allMap = mutableMapOf<String, Int>()
    for (j in teams) allMap[j] = 0
    for (i in text.split(";")) {
        val a = Regex("""^\S+""").find(i.trim())?.value.toString()
        val b = Regex("""\S+$""").find(i)?.value.toString()
        val first = Regex("""\d+(?=:)""").find(i)?.value?.toInt()
        val second = Regex("""(?<=:)\d+""").find(i)?.value?.toInt()
        if (first!! > second!!) {
            allMap[a] = allMap[a]!! + 3
            continue
        }
        if (first < second) {
            allMap[b] = allMap[b]!! + 3
            continue
        } else {
            allMap[a] = allMap[a]!! + 1
            allMap[b] = allMap[b]!! + 1
        }

    }
    for ((i, j) in allMap.toList().sortedBy { (key, value) -> value }.reversed()) {
        fin += i
    }
    return fin
}

/*
* В файле с именем inputName задана таблица действительных чисел.
* Столбцы таблицы разделены запятыми и пробелами.
* Каждая строка содержит не более 26 значений. Пример:
*
* 1.5, 2.67, 3.0, 1.4
* 5.2, 7.1, -4.8, 0.0
* 1.4, 6.0, 2.5, -1.9
* В строковом параметре range  задан диапазон из двух ячеек
* этой таблицы, разделённых чёрточкой, например “A2-C4” или
* “A31-B42”
* Ячейки закодированы так: столбец задаётся заглавной буквой
* латинского алфавита (первый столбец это буква А),
* а строка - целым числом (первая строка это число 1).
*
* Необходимо посчитать среднее арифметическое значений во всех
* ячейках заданного диапазона заданной таблицы. Диапазон задаёт
* углы прямоугольника -- например “А2-С3” соответствует
* ячейкам A2, A3, B2, B3, C2, C3
*
* “Удовлетворительно” -- все строки содержат одинаковое
* количество чисел, заданный диапазон относится к одной строке,
* первая ячейка в нём обязательно находится слева,
* например, “B3-D3” (содержит B3, C3, D3)
*
* “Хорошо” -- диапазоны могут содержать ячейки из разных строк
* с произвольным положением углов, например, “B1-A2”
* соответствует ячейкам A1, A2, B1, B2
*
* “Отлично” -- строки могут содержать разное количество
* чисел. Кроме того, диапазон может включать ячейки за пределами
* входной таблицы, это не является ошибкой,
* ячейки за пределами таблицы просто не учитываются.
* Пример: диапазон “E1-B2” содержит B1, C1, D1, B2, C2, D2
*
* При нарушении форматов входных данных следует выбрасывать
* исключение IllegalArgumentException. При невозможности
* прочитать файл выбрасывать исключение IOException.
*
* Предложить самостоятельно имя функции. Кроме функции следует
* написать тесты, подтверждающие её работоспособность.
*
* 1.5, 2.67, 3.0, 1.4, 8.6
* 5.2, 7.1, -4.8, 0.0, 6.2
* 1.4, 6.0, 2.5, -1.9, -9.9
* throw NumberFormatException("Description")
* A2-C3
*/

fun middle(inputName: String, range: String): Double {
    val table = mutableMapOf<String, List<String>>()
    var count = 0
    var score = 0.0
    val first = Regex("""^[A-Z](?=\d)""").find(range)?.value
    val firstN = Regex("""\d(?=-)""").find(range)?.value
    val second = Regex("""(?<=-)[A-Z]""").find(range)?.value
    val secN = Regex("""(?<=\w)\d$""").find(range)?.value
    if (first == null || firstN == null || second == null || secN == null) {
        exp(1)
    }
    try {
        File(inputName).forEachLine {
            val line = it.split(" ")
            if (Regex("""[^\d.\-,\s]""").find(it)?.value != null) {
                exp(1)
            }
            for (i in line) {
                val b = (line.indexOf(i) + 65).toChar().toString()
                if (table[b + ""] == null) {
                    table[b] = listOf(i.replace(",", ""))
                } else {
                    table[b] =
                        table[b]!!.plus(i.replace(",", ""))
                }
            }
        }
        for ((i, j) in table) {
            if (i.first() in first!!.first()..second!!.first()) {
                for (numb in j) {
                    if (j.indexOf(numb) + 1 in firstN!!.toInt()..secN!!.toInt()) {
                        count++
                        score += numb.toDouble()
                    }
                }
            }
        }
        return score / count.toDouble()
    } catch (e: java.io.FileNotFoundException) {
        exp(2)
    }
    return TODO("Provide the return value")
}

fun exp(type: Int) {
    when (type) {
        1 -> throw IllegalArgumentException("SUS, неправельный ввод данных")
        2 -> throw IOException("ЫЫЫ, файл не прочитать")
    }
}

/*
* В файле с именем inputName заданы ежедневные сведения о
* количестве выпавших осадков (в мм) в различные месяцы года,
* всего не более чем 31 значение в каждой строке и
* не более 12 строк во всём файле, например:
*
* Март 0 1 0 3 41 2 0 0 13 16 20 8 0 4 8 1 0 0 0 7 12 0 4 9
* Апрель 0 0 0 17 0 0 11 48 42 0 0 1 7 15 18 0 0 0 0 0 8 2 17 0
* Май 10 15 48 21 0 0 17 22 30 0 0 13 0 0 2 5 7 0 0 0 1 10 3
*
* Каждая строка начинается с названия месяца, за которым
* следует последовательность целых чисел - уровень осадков в мм
* в различные дни этого месяца, начиная с 1-го. Порядок месяцев
* в файле должен соответствовать реальному (следующий месяц всегда
* ниже предыдущего).
*
* В строковом параметре days задан интервал дат
* либо в формате “Апрель 9..15”  (дни в одном месяце),
* либо в формате “Март 22..Май 8” (дни в разных месяцах).
*
* Необходимо рассчитать максимальный уровень осадков за один день
* в заданном интервале дат. Например, для “Апрель 9..15” это 42,
* для “Март 22..Май 8” это 48. Отсутствующие дни игнорировать.
*
* “Удовлетворительно” -- используется только первый формат для
* параметра days - все дни в одном месяце
*
* “Хорошо” -- может использоваться как первый, так и второй
* формат для параметра days, то есть, интервал может содержать
* дни в разных месяцах
*
* “Отлично” -- результат функции должен содержать не только
* максимальный уровень осадков, но и список дней,
* в которых он был достигнут
* (42, 9 апреля или 48, 8 апреля, 3 мая для примеров выше)
*
* При нарушении форматов входных данных следует выбрасывать
* исключение IllegalArgumentException. При невозможности
* прочитать файл выбрасывать исключение IOException.
*
* Предложить имя и тип результата функции. Кроме функции
* следует написать тесты, подтверждающие её работоспособность.
*/

fun mm(inputName: String, days: String): Any {
    var ans = 0
    val month = listOf<String>(
        "Январь", "Февраль", "Март", "Апрель",
        "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"
    )
    val first = Regex("""^[А-Яа-я]+(?=\s)""").find(days)?.value
    val firstN = Regex("""\d+(?=..)""").find(days)?.value
    val second = Regex("""(?<=\.)[А-Яа-я]+""").find(days)?.value
    val secN = Regex("""(?<=\s)\d+$""").find(days)?.value
    val table = mutableMapOf<String, List<String>>()
    File(inputName).forEachLine {
        val line = it.split(" ").toMutableList()
        if (Regex("""[^\d\+А-Яа-я\s]""").find(it)?.value != null) {
            exp(1)
        }
        val b = it.replace(line[0], "")
        table[line[0]] = b.trim().split(" ")
    }
    for ((i, j) in table) {
        if (month.indexOf(i) in month.indexOf(first)..month.indexOf(second)) {
            for (numb in j) {
                if ((i == first && j.indexOf(numb) + 1 >= firstN!!.toInt()) ||
                    (i == second && j.indexOf(numb) + 1 <= firstN!!.toInt()) ||
                    (i != first && i != second)
                ) {
                    if (numb.toInt() > ans) ans = numb.toInt()
                }
            }
        }
    }
    return ans
}

/*
* В файле с именем прийМате содержатся результаты гонок Формуль-1 в
формате

* Гран-при Австралии:
* Л. Хэмилтон, Мерседес, 8
* В. Боттас, Мерседес, 6
* Л. Норрис, Макларен, 5
* д. Риккардо, Макларен, 4
* Ш Леклер, Феррари, 3
* С. Феттель, Феррари, 2
* М. Ферстапен, Ред Булл, 1
*
* Гран-при России:
* А. Албон, Ред Булл, 8
* Л. Хэмилтон, Мерседес, 5
* С. Феттель, Феррари, 4
* Ш Леклер, Феррари, 2
* В. Боттас, Мерседес, 1

* В первой строке содержится название гран-при, далее идет список пилотов.
* Каждая строка этого списка начинается с имени и фамилии пилота, далее через
* запятую идёт название его команды и число набранных им очкое:
* В команде может быть не более трёх пилотов, число Гран-при и команд - любое.

* "Убовлетворительно” —- определить команду-победителя и число её очков
* (чемпионат состоит из всех указанных е файле Гран-при). В примере: Мерседес, 20

* "Хорошо" - отсортировать команды по набранному количеству очков,
* а при одинаковом числе очков — по алфавиту
* В примере: Мерседес, 20, Феррари, 11, Макларен, 9, Ред Булл, 9

* "Отлично" — отсортировать команды тем же образом.
* Для каждой команды определить, сколько очков набрал каждый из её пилотов.
* В примере: Мерседес, 20 (Л. Хэмилтон 13, В. Боттас 7),
* Феррари, 11 (С. Феттель, 6, Ш. Леклер, 5), Макларен, 9 (Д. Риккардо, 9),
* Ред Булл, 9 (А. Албон, 8, М. Ферстаппен, 1),

* Предложить имя и тип результата функции. Кроме функции
* следует написать тесты, подтверждающие её работоспособность.
*/

fun race(inputName: String): Any {
    val table = mutableMapOf<String, Int>()
    File(inputName).forEachLine {
        val b = Regex("""(?<=,\s)[А-Я\sа-я]+(?=,)""").find(it)?.value
        if (b != null) {
            if (table[b] != null) {
                table[b] = Regex("""\d+$""").find(it)!!.value.toInt() + table[b]!!
            } else {
                table[b] = Regex("""\d+$""").find(it)!!.value.toInt()
            }
        }
    }
    val a = table.entries.sortedBy { it.key }.sortedByDescending { it.value }.toString()
    return a
}


/*
* Про шашки или что там у них:
* Из файла делаем 2 массива, один верикальный, другой горизонтальный
* Затем берём координаты и идем смотреть на строчки в массивах
* Проверяем на наличие Regex("""\*o-"""), если есть обрабатываем дальше
* Посимвольно смотрим на символы :) Если нашли * ищем o и записываем их в лист, если координата конца - совпадает с изначальными кордами,
* то всё ок, выводим список
* Если же не в одном мапе не было соответствий кидаем искличение или то, что от нас требуют
 */

fun twitch(inputName: String): Any {
    val all = mutableMapOf<String, Int>()
    File(inputName).forEachLine {
        val b = Regex("""[\wА-Яа-я_\-\d]+(?=:)""").find(it)?.value
        val minInHour = Regex("""\d+(?=m,)""").find(it)?.value?.toInt()?.div(60.0)
        val hour = Regex("""(?<=:\s)\d+(?=h)""").find(it)?.value?.toDouble()
        val watch = Regex("""\d+$""").find(it)?.value?.toInt()
        val a = it.split(", ").toMutableList()
        a.removeAt(0)
        val list = mutableListOf<Int>()
        for (j in a.indices) {
            list.add(a[j].toInt())
        }
        var last = list.last()
        if (minInHour == 0.0) {
            last = 0
        } else list.removeLast()
        if (b == null || minInHour == null || hour == null || watch == null) exp(1)
        if (all[b] != null) {
            all[b!!] = (all[b]!! + list.sum() + (last * minInHour!!)).toInt()
        } else {
            all[b!!] = (0 + list.sum() + last * minInHour!!).toInt()     //написаь экс на ноль тайма
        }
    }
    val buff = all.entries.sortedBy { it.key }.sortedByDescending { it.value }
    return buff
}