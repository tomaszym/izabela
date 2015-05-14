package org.tejo.model

sealed abstract class Posteno

sealed abstract class Komitatano extends Posteno

case object KomitatanoA extends Komitatano
case object KomitatanoB extends Komitatano
case object KomitatanoC extends Komitatano

case object Redaktoro extends Posteno

case object Volontulo extends Posteno

sealed abstract class Estrarano extends Posteno

case object Prezidanto extends Estrarano
case object Vicprezidanto extends Estrarano
case object Gxensek extends Estrarano
case object Kasisto extends Estrarano
case object AnoDeEstraro extends Estrarano

case object Komisiito extends Posteno