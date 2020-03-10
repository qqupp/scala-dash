package com.github.qqupp.scaladash.model.panel.properties

import io.circe.{Encoder, Json}

sealed class YAxisUnit(val value: String)

object YAxisUnit {

  case object Misc {
    case object NoFormat extends YAxisUnit("none")
    case object Short extends YAxisUnit("short")
    case object Bytes extends YAxisUnit("bytes")
    case object Bits extends YAxisUnit("bits")
    case object BitsPerSecond extends YAxisUnit("bps")
    case object Seconds extends YAxisUnit("s")
    case object Milliseconds extends YAxisUnit("ms")
    case object Microseconds extends YAxisUnit("µs")
    case object Nanoseconds extends YAxisUnit("ns")
    case object Percent extends YAxisUnit("percent")
    case object Percent_0_100 extends YAxisUnit("percent")
    case object Percent_0_1 extends YAxisUnit("percentunit")
    case object Humidity extends YAxisUnit("humidity")
    case object Decibel extends YAxisUnit("dB")
    case object HexadecimalOX extends YAxisUnit("hex0x")
    case object Hexadecimal extends YAxisUnit("hex")
    case object ScientificNotation extends YAxisUnit("sci")
    case object LocaleFormat extends YAxisUnit("locale")
  }

  case object Acceleration {
    case object MetersSecondSquare extends YAxisUnit("accMS2")
    case object FeetSecondSquare extends YAxisUnit("accFS2")
    case object GUnit extends YAxisUnit("accG")
  }

  case object  Angle {
    case object Degrees extends YAxisUnit("degree")
    case object Radians extends YAxisUnit("radian")
    case object Gradian extends YAxisUnit("grad")
    case object ArcMinutes extends YAxisUnit("arcmin")
    case object ArcSeconds extends YAxisUnit("arcsec")
  }

  case object Area {
    case object SquareMeters extends YAxisUnit("areaM2")
    case object SquareFeet extends YAxisUnit("areaF2")
    case object SquareMiles extends YAxisUnit("areaMI2")
  }

  case object Computation {
    case object FLOPS extends YAxisUnit("flops")
    case object MFLOPS extends YAxisUnit("mflops")
    case object GFLOPS extends YAxisUnit("gflops")
    case object TFLOPS extends YAxisUnit("tflops")
    case object PFLOPS extends YAxisUnit("pflops")
    case object EFLOPS extends YAxisUnit("eflops")
    case object ZFLOPS extends YAxisUnit("zflops")
    case object YFLOPS extends YAxisUnit("yflops")
  }

  case object Concentration {
    case object PartsPerMillion extends YAxisUnit("ppm")
    case object PartsPerBillion extends YAxisUnit("conppb")
    case object NanogramPerCubicMeter extends YAxisUnit("conngm3")
    case object NanogramPerNormalCubicMeter extends YAxisUnit("conngNm3")
    case object MicrogramPerCubicMeter extends YAxisUnit("conÎ¼gm3")
    case object MicrogramPerNormalCubicMeter extends YAxisUnit("conÎ¼gNm3")
    case object MilligramPerCubicMeter extends YAxisUnit("conmgm3")
    case object MilligramPerNormalCubicMeter extends YAxisUnit("conmgNm3")
    case object GramPerCubicMeter extends YAxisUnit("congm3")
    case object GramPerNormalCubicMeter extends YAxisUnit("congNm3")
    case object MilligramsPerDecilitre extends YAxisUnit("conmgdL")
    case object MillimolesPerLitre extends YAxisUnit("conmmolL")
  }

  case object Currency {
    case object Dollars extends YAxisUnit("currencyUSD")
    case object Pounds extends YAxisUnit("currencyGBP")
    case object Euro extends YAxisUnit("currencyEUR")
    case object Yen extends YAxisUnit("currencyJPY")
    case object Rubles extends YAxisUnit("currencyRUB")
    case object Hryvnias extends YAxisUnit("currencyUAH")
    case object Real extends YAxisUnit("currencyBRL")
    case object DanishKrone extends YAxisUnit("currencyDKK")
    case object IcelandicKrana extends YAxisUnit("currencyISK")
    case object NorwegianKrone extends YAxisUnit("currencyNOK")
    case object SwedishKrona extends YAxisUnit("currencySEK")
    case object CzechKoruna extends YAxisUnit("currencyCZK")
    case object SwissFranc extends YAxisUnit("currencyCHF")
    case object PolishPNL extends YAxisUnit("currencyPLN")
    case object Bitcoi extends YAxisUnit("currencyBTC")
    case object SouthAfricanRand extends YAxisUnit("currencyZAR")
  }

  case object Data {
    case object Bits extends YAxisUnit("bits")
    case object Bytes extends YAxisUnit("bytes")
    case object Kibibytes extends YAxisUnit("kbytes")
    case object Mebibytes extends YAxisUnit("mbytes")
    case object Gibibytes extends YAxisUnit("gbytes")
    case object Tebibytes extends YAxisUnit("tbytes")
    case object Pebibytes extends YAxisUnit("pbytes")
    case object DecaBits extends YAxisUnit("decbits")
    case object DecaBytes extends YAxisUnit("decbytes")
    case object DecaKilobytes extends YAxisUnit("deckbytes")
    case object DecaMegabytes extends YAxisUnit("decmbytes")
    case object DecaGigabytes extends YAxisUnit("decgbytes")
    case object DecaTerabytes extends YAxisUnit("dectbytes")
    case object DecaPetabytes extends YAxisUnit("decpbytes")
  }

  case object DataRate {
    case object PacketsPerSeconds extends YAxisUnit("pps")
    case object BitsPerSeconds extends YAxisUnit("bps")
    case object BytesPerSeconds extends YAxisUnit("Bps")
    case object KilobytesPerSeconds extends YAxisUnit("KBs")
    case object KilobitsPerSeconds extends YAxisUnit("Kbits")
    case object MegabytesPerSeconds extends YAxisUnit("MBs")
    case object MegabitsPerSeconds extends YAxisUnit("Mbits")
    case object GigabytesPerSeconds extends YAxisUnit("GBs")
    case object GigabitsPerSeconds extends YAxisUnit("Gbits")
    case object TerabytesPerSeconds extends YAxisUnit("TBs")
    case object TerabitsPerSeconds extends YAxisUnit("Tbits")
    case object PetabytesPerSeconds extends YAxisUnit("PBs")
    case object PetabitsPerSeconds extends YAxisUnit("Pbits")
  }

  case object DateTime {
    case object YYYYMMDD_HHmmss extends YAxisUnit("dateTimeAsIso")
    case object DDMMYYYY_hmmss extends YAxisUnit("dateTimeAsUS")
    case object FromNow extends YAxisUnit("dateTimeFromNow")
  }

  case object Energy {
    case object Watt extends YAxisUnit("watt")
    case object Kilowatt extends YAxisUnit("kwatt")
    case object Milliwatt extends YAxisUnit("mwatt")
    case object WattPerSquareMeter extends YAxisUnit("Wm2")
    case object VoltAmpere extends YAxisUnit("voltamp")
    case object KilovoltAmpere extends YAxisUnit("kvoltamp")
    case object VoltAmpereReactive extends YAxisUnit("voltampreact")
    case object KilovoltAmpereReactive extends YAxisUnit("kvoltampreact")
    case object WattHour extends YAxisUnit("watth")
    case object KilowattHour extends YAxisUnit("kwatth")
    case object KilowattMin extends YAxisUnit("kwattm")
    case object AmpereHour extends YAxisUnit("amph")
    case object Joule extends YAxisUnit("joule")
    case object Electronvolt extends YAxisUnit("ev")
    case object Ampere extends YAxisUnit("amp")
    case object Kiloampere extends YAxisUnit("kamp")
    case object Milliampere extends YAxisUnit("mamp")
    case object Volt extends YAxisUnit("volt")
    case object Kilovolt extends YAxisUnit("kvolt")
    case object Millivolt extends YAxisUnit("mvolt")
    case object DecibelMilliwatt extends YAxisUnit("dBm")
    case object Ohm extends YAxisUnit("ohm")
    case object Lumens extends YAxisUnit("lumens")
  }

  case object Flow {
    case object GallonsPerMin extends YAxisUnit("flowgpm")
    case object CubicMetersPerSec extends YAxisUnit("flowcms")
    case object CubicFeetPerSec extends YAxisUnit("flowcfs")
    case object CubicFeetPerMin extends YAxisUnit("flowcfm")
    case object LitrePerHour extends YAxisUnit("litreh")
    case object LitrePerMin extends YAxisUnit("flowlpm")
    case object MilliLitrePerMin extends YAxisUnit("flowmlpm")
    case object Lux extends YAxisUnit("lux")
  }

  case object Force {
    case object NewtonMeters extends YAxisUnit("forceNm")
    case object KilonewtonMeters extends YAxisUnit("forcekNm")
    case object Newtons extends YAxisUnit("forceN")
    case object Kilonewtons extends YAxisUnit("forcekN")
  }

  case object HashRate {
    case object HashesPerSeconds extends YAxisUnit("Hs")
    case object KilohashesPerSec extends YAxisUnit("KHs")
    case object MegahashesPerSec extends YAxisUnit("MHs")
    case object GigahashesPerSec extends YAxisUnit("GHs")
    case object TerahashesPerSec extends YAxisUnit("THs")
    case object PetahashesPerSec extends YAxisUnit("PHs")
    case object ExahashesPerSec extends YAxisUnit("EHs")
  }

  case object Mass {
    case object Milligram extends YAxisUnit("massmg")
    case object Gram extends YAxisUnit("massg")
    case object Kilogram extends YAxisUnit("masskg")
    case object Metricton extends YAxisUnit("masst")
  }

  case object Length {
    case object Millimetre extends YAxisUnit("lengthmm")
    case object Feet extends YAxisUnit("lengthft")
    case object Meter extends YAxisUnit("lengthm")
    case object Kilometer extends YAxisUnit("lengthkm")
    case object Mile extends YAxisUnit("lengthmi")
  }

  case object Pressure {
    case object Millibars extends YAxisUnit("pressurembar")
    case object Bars extends YAxisUnit("pressurebar")
    case object Kilobars extends YAxisUnit("pressurekbar")
    case object Hectopascals extends YAxisUnit("pressurehpa")
    case object Kilopascals extends YAxisUnit("pressurekpa")
    case object InchesOfMercury extends YAxisUnit("pressurehg")
    case object PSI extends YAxisUnit("pressurepsi")
  }

  case object Radiation {
    case object Becquerel extends YAxisUnit("radbq")
    case object Curie extends YAxisUnit("radci")
    case object Gray extends YAxisUnit("radgy")
    case object Rad extends YAxisUnit("radrad")
    case object Sievert extends YAxisUnit("radsv")
    case object Rem extends YAxisUnit("radrem")
    case object ExposureCkg extends YAxisUnit("radexpckg")
    case object Roentgen extends YAxisUnit("radr")
    case object SievertPerHour extends YAxisUnit("radsvh")
  }

  case object Temperature {
    case object Celsius extends YAxisUnit("celsius")
    case object Farenheit extends YAxisUnit("farenheit")
    case object Kelvin extends YAxisUnit("kelvin")
  }

  case object Time {
    case object Hertz extends YAxisUnit("hertz")
    case object Nanoseconds extends YAxisUnit("ns")
    case object Microseconds extends YAxisUnit("Âµs")
    case object Milliseconds extends YAxisUnit("ms")
    case object Seconds extends YAxisUnit("s")
    case object Minutes extends YAxisUnit("m")
    case object Hours extends YAxisUnit("h")
    case object Days extends YAxisUnit("d")
    case object Duration_ms extends YAxisUnit("dtdurationms")
    case object Duration_s extends YAxisUnit("dtdurations")
    case object Duration_hhmmss extends YAxisUnit("dthms")
    case object Timeticks_SecondsPer100 extends YAxisUnit("timeticks")
    case object Clock_ms extends YAxisUnit("clockms")
    case object Clock_s extends YAxisUnit("clocks")
  }

  case object Throughput {
    case object CountsPerSec extends YAxisUnit("cps")
    case object OpsPerSec extends YAxisUnit("ops")
    case object RequestsPerSec extends YAxisUnit("reqps")
    case object ReadsPerSec extends YAxisUnit("rps")
    case object WritesPerSec extends YAxisUnit("wps")
    case object IOopsPerSec extends YAxisUnit("iops")
    case object CountsPerMin extends YAxisUnit("cpm")
    case object OpsPerMin extends YAxisUnit("opm")
    case object ReadsPerMin extends YAxisUnit("rpm")
    case object WritesPerMin extends YAxisUnit("wpm")
  }

  case object Velocity {
    case object MetresPerSecond extends YAxisUnit("velocityms")
    case object KilometersPerHour extends YAxisUnit("velocitykmh")
    case object MilesPerHour extends YAxisUnit("velocitymph")
    case object Knot extends YAxisUnit("velocityknot")
  }

  case object Volume {
    case object Millilitre extends YAxisUnit("mlitre")
    case object Litre extends YAxisUnit("litre")
    case object CubicMetre extends YAxisUnit("m3")
    case object NormalCubicMetre extends YAxisUnit("Nm3")
    case object CubicDecimetre extends YAxisUnit("dm3")
    case object Gallons extends YAxisUnit("gallons")
  }


  implicit val jsonEncoder: Encoder[YAxisUnit] =
      axeUnit =>
        Json.fromString(axeUnit.value)

}