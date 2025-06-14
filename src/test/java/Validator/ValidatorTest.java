package Validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

    // Тесты для validateParams()
    @ParameterizedTest
    @CsvSource({
            "-3, -5, 1, true",    // Нижние границы
            "5, 5, 3, true",       // Верхние границы
            "0, 0, 2, true",       // Нулевые значения
            "1.5, 2.5, 2.5, true", // Дробные значения (r кратно 0.5)
            "-4, 2, 2, false",     // x < -3
            "2, -6, 2, false",     // y < -5
            "2, 6, 2, false",      // y > 5
            "2, 2, 0.5, false",    // r < 1
            "2, 2, 3.5, false",    // r > 3
            "2, 2, 1.3, false"     // r не кратно 0.5
    })
    void testValidateParams(float x, float y, float r, boolean expected) {
        assertEquals(expected, Validator.validateParams(x, y, r));
    }

    // Тесты для checkPointInArea() и его компонентов
    @ParameterizedTest
    @CsvSource({
            // Прямоугольник (2-я четверть)
            "-1, 1, 2, true",     // Внутри
            "-2, 0, 2, true",      // На границе
            "-3, 1, 2, false",     // За левой границей
            "-1, 3, 2, false",     // За верхней границей

            // Сектор круга (4-я четверть)
            "0, -1, 2, true",      // На границе по X
            "1, 0, 2, true",       // На границе по Y
            "1.5, -1.5, 2, false", // За пределами круга

            // Треугольник (1-я четверть)
            "0.5, 0.5, 1, true",   // Внутри
            "1, 0, 1, true",       // На границе (X)
            "0, 1, 1, true",       // На границе (Y)
            "0.6, 0.6, 1, false",  // За гипотенузой

            // Вне всех областей
            "-1, -1, 2, false",    // 3-я четверть
            "2, 2, 2, false"       // Далеко в 1-й четверти
    })
    void testCheckPointInArea(float x, float y, float r, boolean expected) {
        assertEquals(expected, Validator.checkPointInArea(x, y, r));
    }

    // Тесты для граничных значений R
    @Test
    void testWithMinR() {
        assertTrue(Validator.validateParams(0, 0, 1));
        assertTrue(Validator.checkPointInArea(0, 0, 1));
    }

    @Test
    void testWithMaxR() {
        assertTrue(Validator.validateParams(0, 0, 3));
        assertTrue(Validator.checkPointInArea(0, 0, 3));
    }

}