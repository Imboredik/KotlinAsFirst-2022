package playground

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Tests {
    @Test
    fun myFun() {
        assertEquals(
            listOf("Зенит", "Спартак", "Подмоскович", "Барнаулец", "ЦСКА"), // Что ожидаете
            playground.myFun(
                "Зенит 0:1 Спартак; Зенит 1:0 ЦСКА; Барнаулец 0:2 Зенит; Подмоскович 4:4 Барнаулец",
                listOf("Зенит", "Спартак", "ЦСКА", "Барнаулец", "Подмоскович")
            )
        )
    }

    @Test
    fun middle() {
        assertEquals(
            (2.9), // Что ожидаете
            playground.middle("input/middle.txt", "A2-C3")
        )
    }

    @Test
    fun mm() {
        assertEquals(
            (48), // Что ожидаете
            playground.mm("input/mm.txt", "Март 22..Май 8")
        )
    }

    @Test
    fun race() {
        assertEquals(
            ("[Мерседес=20, Феррари=11, Макларен=9, Ред Булл=9]"), // Что ожидаете
            playground.race("input/race.txt")
        )
    }


}

/*
assertThrows(
IllegalArgumentException::class.java // (название ошибки)
) {
    playground.myFun()
}
*/