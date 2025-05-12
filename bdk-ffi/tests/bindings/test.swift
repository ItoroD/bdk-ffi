/*
 * This is a basic test swift program that does nothing but confirm that the swift bindings compile
 * and that a program that depends on them will run.
 */

import Foundation
import BitcoinDevKit

// A type from the bitcoin-ffi library
let network = Network.testnet

// Pass a random 64 character hex to convert to 32 bytes array
if let hashData = Data(hex: "e31d76e04fa2e03dfa0b6bc0be9e14efe31d76e04fa2e03dfa0b6bc0be9e14ef") {
// A type from the bdk-ffi library
    let blockId = try? BlockId(height: 0, hash: BlockHash.fromBytes(bytes: hashData))
}

// convert to 32 bytes array
extension Data {
    init?(hex: String) {
        self.init()

        var currentIndex = hex.startIndex
        while currentIndex < hex.endIndex {
            let nextIndex = hex.index(currentIndex, offsetBy: 2)
            guard nextIndex <= hex.endIndex,
                  let byte = UInt8(hex[currentIndex..<nextIndex], radix: 16) else {
                return nil // Return nil if the string isn’t valid hexadecimal
            }
            self.append(byte)
            currentIndex = nextIndex
        }
    }
}
