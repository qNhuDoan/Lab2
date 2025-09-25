import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty
open class SmartDevice (val name: String, val category: String) {
    val number: Int = 1
    var deviceStatus = "online"
        protected set(value) {
            field = value
        }
    open val deviceType = "unknown"
    constructor(name: String, category: String, statusCode: Int) : this(name, category) {
        deviceStatus = when (statusCode) {
            0 -> "offline"
            1 -> "online"
            else -> "unknown"
        }
    }
//    open fun turnOn() {
//        println("Smart is turned on")
//        turnOff()
//    }
//    open fun turnOff() {
//        println("Smart is turned off")
//    }
    open fun turnOn() {
        deviceStatus = "on"
    }

    open fun turnOff() {
        deviceStatus = "off"
    }
//    var speakerVolume = 2
//        get() = field
//        set(value) {
//            if (value in 0..100) {
//                field = value
//            }
//        }

}
class SmartTvDevice(deviceName: String, deviceCategory: String) :
    SmartDevice(name = deviceName, category = deviceCategory) {
    override val deviceType = "Smart TV"
    
    private var speakerVolume = 2
        set(value) {
            if (value in 0..100) {
                field = value
            }
        }
    private var channelNumber = 1
        set(value) {
            if (value in 0..200) {
                field = value
            }
        }
    fun increaseSpeakerVolume() {
        speakerVolume++
        println("Speaker volume increased to $speakerVolume.")
    }
    fun nextChannel() {
        channelNumber++
        println("Channel number increased to $channelNumber.")
    }
    override fun turnOn() {
//        deviceStatus = "on"
        super.turnOn()
        println(
            "$name is turned on. Speaker volume is set to $speakerVolume and channel number is " +
                    "set to $channelNumber."
        )
    }
    override fun turnOff() {
//        deviceStatus = "off"
        super.turnOff()
        println("$name turned off")
    }
}
class SmartLightDevice(deviceName: String, deviceCategory: String) :
    SmartDevice(name = deviceName, category = deviceCategory) {
    override val deviceType = "Smart Light"
    var brightnessLevel = 0
        set(value) {
            if (value in 0..100) {
                field = value
            }
        }

    fun increaseBrightness() {
        brightnessLevel++
        println("Brightness increased to $brightnessLevel.")
    }
    override fun turnOn() {
//        deviceStatus = "on"
        super.turnOn()
        brightnessLevel = 2
        println("$name turned on. The brightness level is $brightnessLevel.")
    }

    override fun turnOff() {
//        deviceStatus = "off"
        super.turnOff()
        brightnessLevel = 0
        println("Smart Light turned off")
    }
}
class SmartHome(val smartTvDevice: SmartTvDevice, val smartLightDevice: SmartLightDevice) {
    var deviceTurnOnCount = 0
        private set
    fun turnOnTv() {
        deviceTurnOnCount++
        smartTvDevice.turnOn()
    }

    fun turnOffTv() {
        deviceTurnOnCount--
        smartTvDevice.turnOff()
    }

    fun increaseTvVolume() {
        deviceTurnOnCount++
        smartTvDevice.increaseSpeakerVolume()
    }

    fun changeTvChannelToNext() {
        deviceTurnOnCount--
        smartTvDevice.nextChannel()
    }

    fun turnOnLight() {
        smartLightDevice.turnOn()
    }

    fun turnOffLight() {
        smartLightDevice.turnOff()
    }

    fun increaseLightBrightness() {
        smartLightDevice.increaseBrightness()
    }

    fun turnOffAllDevices() {
        turnOffTv()
        turnOffLight()
    }
}
class RangeRegulator(
    initialValue: Int,
    private val minValue: Int,
    private val maxValue: Int
) : ReadWriteProperty<Any?, Int> {
    var fieldData = initialValue
    override fun getValue(thisRef: Any?, property: KProperty<*>): Int {
        return fieldData
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
        if (value in minValue..maxValue) {
            fieldData = value
        }
    }
}
fun main() {
    val SmartTvDevice = SmartDevice("Android TV", "Entertainment", 0)
    println("Device number is: ${SmartTvDevice.number}")
    println("Device name is: ${SmartTvDevice.name}")
    println("Device category is: ${SmartTvDevice.category}")
    println("Device deviceStatus is: ${SmartTvDevice.deviceStatus}")
    SmartTvDevice.turnOn()
    SmartTvDevice.turnOff()
//    println(SmartTvDevice.speakerVolume)
//    SmartTvDevice.speakerVolume = 50
//    println(SmartTvDevice.speakerVolume)
    println("Tv status: ${SmartTvDevice.deviceStatus}")
    var smartDevice: SmartDevice = SmartTvDevice("Android TV", "Entertainment")
    smartDevice.turnOn()

    smartDevice = SmartLightDevice("Google Light", "Utility")
    smartDevice.turnOn()

}