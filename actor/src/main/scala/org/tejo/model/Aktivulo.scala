package org.tejo.model

sealed abstract class Aktivulo

sealed abstract class Komitatano extends Aktivulo

case object KomitatanoA extends Komitatano
case object KomitatanoB extends Komitatano
case object KomitatanoC extends Komitatano

case object Redaktoro extends Aktivulo

case object Volontulo extends Aktivulo

sealed abstract class Estrarano extends Aktivulo

case object Prezidanto extends Estrarano
case object Vicprezidanto extends Estrarano
case object Gxensek extends Estrarano
case object Kasisto extends Estrarano
case object AnoDeEstraro extends Estrarano

case object Komisiisto extends Aktivulo