/*
 * This is a basic test kotlin program that does nothing but confirm that the kotlin bindings compile
 * and that a program that depends on them will run.
 */

import org.bitcoindevkit.bitcoin.Network
import org.bitcoindevkit.BlockId

// A type from bitcoin-ffi
val network = Network.TESTNET

// A type from bdk-ffi
val blockId = BlockId(0U, BlockHash.fromBytes("e31d76e04fa2e03dfa0b6bc0be9e14efe31d76e04fa2e03dfa0b6bc0be9e14ef".hexToByteArray())) 

// convert hex to 32 bytes array
fun String.hexToByteArray(): ByteArray {
    val cleanInput = this.removePrefix("0x") // Optional: Remove "0x" if present
    require(cleanInput.length % 2 == 0) { "Hex string must have an even length" }

    return ByteArray(cleanInput.length / 2) { i ->
        cleanInput.substring(i * 2, i * 2 + 2).toInt(16).toByte()
    }
}
