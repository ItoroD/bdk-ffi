from bdkpython import BlockId
from bdkpython.bitcoin import Network

import unittest

class TestBdk(unittest.TestCase):

    # A type from the bitcoin-ffi library
    def test_some_enum(self):
        network = Network.TESTNET

    # A type from the bdk-ffi library
    def test_some_dict(self):
        # Pass a random 64 character hex to convert to 32 bytes array
        block_id = BlockId(height=0, hash=bytes.fromhex("e31d76e04fa2e03dfa0b6bc0be9e14efe31d76e04fa2e03dfa0b6bc0be9e14ef")
)

if __name__=='__main__':
    unittest.main()
