package org.tejo.model.google

import java.io.File

case class AccountConfig(
    serviceAccountId: String,
    p12PrivateKey: File
  )
