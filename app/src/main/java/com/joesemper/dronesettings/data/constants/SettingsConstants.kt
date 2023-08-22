package com.joesemper.dronesettings.data.constants

class SettingsConstants {
    companion object {

        // Timeline constants

        // Минимальная задержка до активации всех систем, секунд
        const val MIN_TIME_DELAY = 0

        // Максимальная задержка до активации всех систем, секунд
        const val MAX_TIME_DELAY = 180

        // Минимальное время взведения, секунд
        const val MIN_COCKING_TIME = 0

        // Максимальное время взведения, секунд
        const val MAX_COCKING_TIME = 180

        // Минимальное время самоуничтожения, секунд
        const val MIN_SELF_DESTRUCTION_TIME = 0

        // Максимальное время самоуничтожения, секунд
        const val MAX_SELF_DESTRUCTION_TIME = 600

        // Sensors constants

        // Минимальное расстояние на котором происходит срабатывание, метров
        const val MIN_TARGET_DISTANCE = 0

        // Максимальное расстояние на котором происходит срабатывание, метров
        const val MAX_TARGET_DISTANCE = 5

        // Минимальное напряжение срабатывания, значение по умолчанию, вольт
        const val MIN_VOLTAGE_DEFAULT_VALUE = 0

        // Среднее отклонение, значение по умолчанию
        const val AVERAGE_ACCELERATION_DEFAULT_VALUE = 0

        // Среднее напряжение срабатывания, значение по умолчанию
        const val AVERAGE_DEVIATION_DEFAULT_VALUE = 0

        // Коэффициент отклонения, значение по умолчанию
        const val DEVIATION_COEFFICIENT_DEFAULT_VALUE = 0

        // Мертвое время, значение по умолчанию, секунд
        const val DEAD_TIME_DEFAULT_VALUE = 0

        //Signal constants

        // Ширина импульса взведения высокий уровень, значение по умолчанию, миллисекунд
        const val COCKING_PULSE_DURATION_HIGH_DEFAULT_VALUE = 1

        // Ширина импульса взведения низкий уровень, значение по умолчанию, миллисекунд
        const val COCKING_PULSE_DURATION_LOW_DEFAULT_VALUE = 1

        // Количество импульсов взведения
        const val COCKING_PULSE_AMOUNT_DEFAULT_VALUE = 1

        // Ширина импульса срабатывания высокий уровень, значение по умолчанию, миллисекунд
        const val ACTIVATION_PULSE_DURATION_HIGH_DEFAULT_VALUE = 1

        // Ширина импульса срабатывания низкий уровень, значение по умолчанию, миллисекунд
        const val ACTIVATION_PULSE_DURATION_LOW_DEFAULT_VALUE = 1

        // Количество импульсов срабатывания
        const val ACTIVATION_PULSE_AMOUNT_DEFAULT_VALUE = 1

    }
}