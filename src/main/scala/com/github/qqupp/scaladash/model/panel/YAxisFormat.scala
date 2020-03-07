package com.github.qqupp.scaladash.model.panel

import io.circe.{Encoder, Json}

sealed class YAxisFormat(val value: String)

object YAxisFormat {

  case object Misc {
    case object NoFormat extends YAxisFormat("none")
    case object Short extends YAxisFormat("short")
    case object Bytes extends YAxisFormat("bytes")
    case object Bits extends YAxisFormat("bits")
    case object BitsPerSecond extends YAxisFormat("bps")
    case object Seconds extends YAxisFormat("s")
    case object Milliseconds extends YAxisFormat("ms")
    case object Microseconds extends YAxisFormat("µs")
    case object Nanoseconds extends YAxisFormat("ns")
    case object Percent extends YAxisFormat("percent")
    case object Percent_0_100 extends YAxisFormat("percent")
    case object Percent_0_1 extends YAxisFormat("percentunit")
    case object Humidity extends YAxisFormat("humidity")
    case object Decibel extends YAxisFormat("dB")
    case object HexadecimalOX extends YAxisFormat("hex0x")
    case object Hexadecimal extends YAxisFormat("hex")
    case object ScientificNotation extends YAxisFormat("sci")
    case object LocaleFormat extends YAxisFormat("locale")
  }

  case object Acceleration {
    case object MetersSecondSquare extends YAxisFormat("accMS2")
    case object FeetSecondSquare extends YAxisFormat("accFS2")
    case object GUnit extends YAxisFormat("accG")
  }

  case object  Angle {
    case object Degrees extends YAxisFormat("degree")
    case object Radians extends YAxisFormat("radian")
    case object Gradian extends YAxisFormat("grad")
    case object ArcMinutes extends YAxisFormat("arcmin")
    case object ArcSeconds extends YAxisFormat("arcsec")
  }

  case object Area {
    case object SquareMeters extends YAxisFormat("areaM2")
    case object SquareFeet extends YAxisFormat("areaF2")
    case object SquareMiles extends YAxisFormat("areaMI2")
  }

  case object Computation {
    case object FLOPS extends YAxisFormat("flops")
    case object MFLOPS extends YAxisFormat("mflops")
    case object GFLOPS extends YAxisFormat("gflops")
    case object TFLOPS extends YAxisFormat("tflops")
    case object PFLOPS extends YAxisFormat("pflops")
    case object EFLOPS extends YAxisFormat("eflops")
    case object ZFLOPS extends YAxisFormat("zflops")
    case object YFLOPS extends YAxisFormat("yflops")
  }

  case object Concentration {
    case object PartsPerMillion extends YAxisFormat("ppm")
    case object PartsPerBillion extends YAxisFormat("conppb")
    case object NanogramPerCubicMeter extends YAxisFormat("conngm3")
    case object NanogramPerNormalCubicMeter extends YAxisFormat("conngNm3")
    case object MicrogramPerCubicMeter extends YAxisFormat("conÎ¼gm3")
    case object MicrogramPerNormalCubicMeter extends YAxisFormat("conÎ¼gNm3")
    case object MilligramPerCubicMeter extends YAxisFormat("conmgm3")
    case object MilligramPerNormalCubicMeter extends YAxisFormat("conmgNm3")
    case object GramPerCubicMeter extends YAxisFormat("congm3")
    case object GramPerNormalCubicMeter extends YAxisFormat("congNm3")
    case object MilligramsPerDecilitre extends YAxisFormat("conmgdL")
    case object MillimolesPerLitre extends YAxisFormat("conmmolL")
  }

  case object Currency {
    case object Dollars extends YAxisFormat("currencyUSD")
    case object Pounds extends YAxisFormat("currencyGBP")
    case object Euro extends YAxisFormat("currencyEUR")
    case object Yen extends YAxisFormat("currencyJPY")
    case object Rubles extends YAxisFormat("currencyRUB")
    case object Hryvnias extends YAxisFormat("currencyUAH")
    case object Real extends YAxisFormat("currencyBRL")
    case object DanishKrone extends YAxisFormat("currencyDKK")
    case object IcelandicKrana extends YAxisFormat("currencyISK")
    case object NorwegianKrone extends YAxisFormat("currencyNOK")
    case object SwedishKrona extends YAxisFormat("currencySEK")
    case object CzechKoruna extends YAxisFormat("currencyCZK")
    case object SwissFranc extends YAxisFormat("currencyCHF")
    case object PolishPNL extends YAxisFormat("currencyPLN")
    case object Bitcoi extends YAxisFormat("currencyBTC")
    case object SouthAfricanRand extends YAxisFormat("currencyZAR")
  }

  case object Data {
    case object Bits extends YAxisFormat("bits")
    case object Bytes extends YAxisFormat("bytes")
    case object Kibibytes extends YAxisFormat("kbytes")
    case object Mebibytes extends YAxisFormat("mbytes")
    case object Gibibytes extends YAxisFormat("gbytes")
    case object Tebibytes extends YAxisFormat("tbytes")
    case object Pebibytes extends YAxisFormat("pbytes")
    case object DecaBits extends YAxisFormat("decbits")
    case object DecaBytes extends YAxisFormat("decbytes")
    case object DecaKilobytes extends YAxisFormat("deckbytes")
    case object DecaMegabytes extends YAxisFormat("decmbytes")
    case object DecaGigabytes extends YAxisFormat("decgbytes")
    case object DecaTerabytes extends YAxisFormat("dectbytes")
    case object DecaPetabytes extends YAxisFormat("decpbytes")
  }

  case object DataRate {
    case object PacketsPerSeconds extends YAxisFormat("pps")
    case object BitsPerSeconds extends YAxisFormat("bps")
    case object BytesPerSeconds extends YAxisFormat("Bps")
    case object KilobytesPerSeconds extends YAxisFormat("KBs")
    case object KilobitsPerSeconds extends YAxisFormat("Kbits")
    case object MegabytesPerSeconds extends YAxisFormat("MBs")
    case object MegabitsPerSeconds extends YAxisFormat("Mbits")
    case object GigabytesPerSeconds extends YAxisFormat("GBs")
    case object GigabitsPerSeconds extends YAxisFormat("Gbits")
    case object TerabytesPerSeconds extends YAxisFormat("TBs")
    case object TerabitsPerSeconds extends YAxisFormat("Tbits")
    case object PetabytesPerSeconds extends YAxisFormat("PBs")
    case object PetabitsPerSeconds extends YAxisFormat("Pbits")
  }

  case object DateTime {
    case object YYYYMMDD_HHmmss extends YAxisFormat("dateTimeAsIso")
    case object DDMMYYYY_hmmss extends YAxisFormat("dateTimeAsUS")
    case object FromNow extends YAxisFormat("dateTimeFromNow")
  }

  case object Energy {
    case object Watt extends YAxisFormat("watt")
    case object Kilowatt extends YAxisFormat("kwatt")
    case object Milliwatt extends YAxisFormat("mwatt")
    case object WattPerSquareMeter extends YAxisFormat("Wm2")
    case object VoltAmpere extends YAxisFormat("voltamp")
    case object KilovoltAmpere extends YAxisFormat("kvoltamp")
    case object VoltAmpereReactive extends YAxisFormat("voltampreact")
    case object KilovoltAmpereReactive extends YAxisFormat("kvoltampreact")
    case object WattHour extends YAxisFormat("watth")
    case object KilowattHour extends YAxisFormat("kwatth")
    case object KilowattMin extends YAxisFormat("kwattm")
    case object AmpereHour extends YAxisFormat("amph")
    case object Joule extends YAxisFormat("joule")
    case object Electronvolt extends YAxisFormat("ev")
    case object Ampere extends YAxisFormat("amp")
    case object Kiloampere extends YAxisFormat("kamp")
    case object Milliampere extends YAxisFormat("mamp")
    case object Volt extends YAxisFormat("volt")
    case object Kilovolt extends YAxisFormat("kvolt")
    case object Millivolt extends YAxisFormat("mvolt")
    case object DecibelMilliwatt extends YAxisFormat("dBm")
    case object Ohm extends YAxisFormat("ohm")
    case object Lumens extends YAxisFormat("lumens")
  }

  case object Flow {
    case object GallonsPerMin extends YAxisFormat("flowgpm")
    case object CubicMetersPerSec extends YAxisFormat("flowcms")
    case object CubicFeetPerSec extends YAxisFormat("flowcfs")
    case object CubicFeetPerMin extends YAxisFormat("flowcfm")
    case object LitrePerHour extends YAxisFormat("litreh")
    case object LitrePerMin extends YAxisFormat("flowlpm")
    case object MilliLitrePerMin extends YAxisFormat("flowmlpm")
    case object Lux extends YAxisFormat("lux")
  }

  case object Force {
    case object NewtonMeters extends YAxisFormat("forceNm")
    case object KilonewtonMeters extends YAxisFormat("forcekNm")
    case object Newtons extends YAxisFormat("forceN")
    case object Kilonewtons extends YAxisFormat("forcekN")
  }

  case object HashRate {
    case object HashesPerSeconds extends YAxisFormat("Hs")
    case object KilohashesPerSec extends YAxisFormat("KHs")
    case object MegahashesPerSec extends YAxisFormat("MHs")
    case object GigahashesPerSec extends YAxisFormat("GHs")
    case object TerahashesPerSec extends YAxisFormat("THs")
    case object PetahashesPerSec extends YAxisFormat("PHs")
    case object ExahashesPerSec extends YAxisFormat("EHs")
  }

  case object Mass {
    case object Milligram extends YAxisFormat("massmg")
    case object Gram extends YAxisFormat("massg")
    case object Kilogram extends YAxisFormat("masskg")
    case object Metricton extends YAxisFormat("masst")
  }

  case object Length {
    case object Millimetre extends YAxisFormat("lengthmm")
    case object Feet extends YAxisFormat("lengthft")
    case object Meter extends YAxisFormat("lengthm")
    case object Kilometer extends YAxisFormat("lengthkm")
    case object Mile extends YAxisFormat("lengthmi")
  }

  case object Pressure {
    case object Millibars extends YAxisFormat("pressurembar")
    case object Bars extends YAxisFormat("pressurebar")
    case object Kilobars extends YAxisFormat("pressurekbar")
    case object Hectopascals extends YAxisFormat("pressurehpa")
    case object Kilopascals extends YAxisFormat("pressurekpa")
    case object InchesOfMercury extends YAxisFormat("pressurehg")
    case object PSI extends YAxisFormat("pressurepsi")
  }

  case object Radiation {
    case object Becquerel extends YAxisFormat("radbq")
    case object Curie extends YAxisFormat("radci")
    case object Gray extends YAxisFormat("radgy")
    case object Rad extends YAxisFormat("radrad")
    case object Sievert extends YAxisFormat("radsv")
    case object Rem extends YAxisFormat("radrem")
    case object ExposureCkg extends YAxisFormat("radexpckg")
    case object Roentgen extends YAxisFormat("radr")
    case object SievertPerHour extends YAxisFormat("radsvh")
  }

  case object Temperature {
    case object Celsius extends YAxisFormat("celsius")
    case object Farenheit extends YAxisFormat("farenheit")
    case object Kelvin extends YAxisFormat("kelvin")
  }

  case object Time {
    case object Hertz extends YAxisFormat("hertz")
    case object Nanoseconds extends YAxisFormat("ns")
    case object Microseconds extends YAxisFormat("Âµs")
    case object Milliseconds extends YAxisFormat("ms")
    case object Seconds extends YAxisFormat("s")
    case object Minutes extends YAxisFormat("m")
    case object Hours extends YAxisFormat("h")
    case object Days extends YAxisFormat("d")
    case object Duration_ms extends YAxisFormat("dtdurationms")
    case object Duration_s extends YAxisFormat("dtdurations")
    case object Duration_hhmmss extends YAxisFormat("dthms")
    case object Timeticks_SecondsPer100 extends YAxisFormat("timeticks")
    case object Clock_ms extends YAxisFormat("clockms")
    case object Clock_s extends YAxisFormat("clocks")
  }

  case object Throughput {
    case object CountsPerSec extends YAxisFormat("cps")
    case object OpsPerSec extends YAxisFormat("ops")
    case object RequestsPerSec extends YAxisFormat("reqps")
    case object ReadsPerSec extends YAxisFormat("rps")
    case object WritesPerSec extends YAxisFormat("wps")
    case object IOopsPerSec extends YAxisFormat("iops")
    case object CountsPerMin extends YAxisFormat("cpm")
    case object OpsPerMin extends YAxisFormat("opm")
    case object ReadsPerMin extends YAxisFormat("rpm")
    case object WritesPerMin extends YAxisFormat("wpm")
  }

  case object Velocity {
    case object MetresPerSecond extends YAxisFormat("velocityms")
    case object KilometersPerHour extends YAxisFormat("velocitykmh")
    case object MilesPerHour extends YAxisFormat("velocitymph")
    case object Knot extends YAxisFormat("velocityknot")
  }

  case object Volume {
    case object Millilitre extends YAxisFormat("mlitre")
    case object Litre extends YAxisFormat("litre")
    case object CubicMetre extends YAxisFormat("m3")
    case object NormalCubicMetre extends YAxisFormat("Nm3")
    case object CubicDecimetre extends YAxisFormat("dm3")
    case object Gallons extends YAxisFormat("gallons")
  }


  implicit val jsonEncoder: Encoder[YAxisFormat] =
      yAxisFormat =>
        Json.fromString(yAxisFormat.value)

}