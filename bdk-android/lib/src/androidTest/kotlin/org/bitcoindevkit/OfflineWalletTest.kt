package org.bitcoindevkit

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.runner.RunWith
import java.io.File
import kotlin.test.AfterTest

@RunWith(AndroidJUnit4::class)
class OfflineWalletTest {
    private val persistenceFilePath = InstrumentationRegistry
        .getInstrumentation().targetContext.filesDir.path + "/bdk_persistence1.sqlite"

    @AfterTest
    fun cleanup() {
        val file = File(persistenceFilePath)
        if (file.exists()) {
            file.delete()
        }
    }

    @Test
    fun testDescriptorBip86() {
        val mnemonic: Mnemonic = Mnemonic(WordCount.WORDS12)
        val descriptorSecretKey: DescriptorSecretKey = DescriptorSecretKey(Network.TESTNET, mnemonic, null)
        val descriptor: Descriptor = Descriptor.newBip86(descriptorSecretKey, KeychainKind.EXTERNAL, Network.TESTNET)

        assertTrue(descriptor.toString().startsWith("tr"), "Bip86 Descriptor does not start with 'tr'")
    }

   @Test
    fun testNewAddress() {
        val descriptor: Descriptor = Descriptor(
            "wpkh([c258d2e4/84h/1h/0h]tpubDDYkZojQFQjht8Tm4jsS3iuEmKjTiEGjG6KnuFNKKJb5A6ZUCUZKdvLdSDWofKi4ToRCwb9poe1XdqfUnP4jaJjCB2Zwv11ZLgSbnZSNecE/0/*)",
            Network.TESTNET
        )
        val wallet: Wallet = Wallet(
            descriptor,
            null,
            persistenceFilePath,
            Network.TESTNET
        )
        val addressInfo: AddressInfo = wallet.revealNextAddress(KeychainKind.EXTERNAL)

        assertTrue(addressInfo.address.isValidForNetwork(Network.TESTNET), "Address is not valid for testnet network")
        assertTrue(addressInfo.address.isValidForNetwork(Network.SIGNET), "Address is not valid for signet network")
        assertFalse(addressInfo.address.isValidForNetwork(Network.REGTEST), "Address is valid for regtest network, but it shouldn't be")
        assertFalse(addressInfo.address.isValidForNetwork(Network.BITCOIN), "Address is valid for bitcoin network, but it shouldn't be")

        assertEquals(
            expected = "tb1qzg4mckdh50nwdm9hkzq06528rsu73hjxxzem3e",
            actual = addressInfo.address.toString()
        )
    }

    @Test
    fun testBalance() {
        val descriptor: Descriptor = Descriptor(
            "wpkh([c258d2e4/84h/1h/0h]tpubDDYkZojQFQjht8Tm4jsS3iuEmKjTiEGjG6KnuFNKKJb5A6ZUCUZKdvLdSDWofKi4ToRCwb9poe1XdqfUnP4jaJjCB2Zwv11ZLgSbnZSNecE/0/*)",
            Network.TESTNET
        )
        val wallet: Wallet = Wallet(
            descriptor,
            null,
            persistenceFilePath,
            Network.TESTNET
        )

        assertEquals(
            expected = 0uL,
            actual = wallet.getBalance().total.toSat()
        )
    }
}
