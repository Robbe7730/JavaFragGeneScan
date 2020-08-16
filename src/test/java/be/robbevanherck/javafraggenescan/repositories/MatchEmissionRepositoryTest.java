package be.robbevanherck.javafraggenescan.repositories;

import be.robbevanherck.javafraggenescan.dummies.DummyMatchEmissionRepository;
import be.robbevanherck.javafraggenescan.entities.AminoAcid;
import be.robbevanherck.javafraggenescan.entities.HMMState;
import be.robbevanherck.javafraggenescan.entities.Triple;
import org.junit.Test;

import java.util.Map;
import java.util.Scanner;

import static be.robbevanherck.javafraggenescan.TestUtil.aminoAcids;
import static be.robbevanherck.javafraggenescan.TestUtil.matchingStates;
import static org.junit.Assert.*;

public class MatchEmissionRepositoryTest {
    private final DummyMatchEmissionRepository dummyMatchEmissionRepository = new DummyMatchEmissionRepository();

    @Test
    public void read() {
        // Only the index and block reads need to be checked, all other things are tested in
        // CGDependentRepositoryTest
        Scanner indexScanner = new Scanner("123\n");
        assertEquals(123, dummyMatchEmissionRepository.readIndex(indexScanner));

        Scanner blockScanner = new Scanner(
                "0.0000\t0.0001\t0.0002\t0.0003\n" +
                "0.0004\t0.0005\t0.0006\t0.0007\n" +
                "0.0008\t0.0009\t0.0010\t0.0011\n" +
                "0.0012\t0.0013\t0.0014\t0.0015\n" +
                "0.0016\t0.0017\t0.0018\t0.0019\n" +
                "0.0020\t0.0021\t0.0022\t0.0023\n" +
                "0.0024\t0.0025\t0.0026\t0.0027\n" +
                "0.0028\t0.0029\t0.0030\t0.0031\n" +
                "0.0032\t0.0033\t0.0034\t0.0035\n" +
                "0.0036\t0.0037\t0.0038\t0.0039\n" +
                "0.0040\t0.0041\t0.0042\t0.0043\n" +
                "0.0044\t0.0045\t0.0046\t0.0047\n" +
                "0.0048\t0.0049\t0.0050\t0.0051\n" +
                "0.0052\t0.0053\t0.0054\t0.0055\n" +
                "0.0056\t0.0057\t0.0058\t0.0059\n" +
                "0.0060\t0.0061\t0.0062\t0.0063\n" +
                "0.0064\t0.0065\t0.0066\t0.0067\n" +
                "0.0068\t0.0069\t0.0070\t0.0071\n" +
                "0.0072\t0.0073\t0.0074\t0.0075\n" +
                "0.0076\t0.0077\t0.0078\t0.0079\n" +
                "0.0080\t0.0081\t0.0082\t0.0083\n" +
                "0.0084\t0.0085\t0.0086\t0.0087\n" +
                "0.0088\t0.0089\t0.0090\t0.0091\n" +
                "0.0092\t0.0093\t0.0094\t0.0095\n" +
                "0.0096\t0.0097\t0.0098\t0.0099\n" +
                "0.0100\t0.0101\t0.0102\t0.0103\n" +
                "0.0104\t0.0105\t0.0106\t0.0107\n" +
                "0.0108\t0.0109\t0.0110\t0.0111\n" +
                "0.0112\t0.0113\t0.0114\t0.0115\n" +
                "0.0116\t0.0117\t0.0118\t0.0119\n" +
                "0.0120\t0.0121\t0.0122\t0.0123\n" +
                "0.0124\t0.0125\t0.0126\t0.0127\n" +
                "0.0128\t0.0129\t0.0130\t0.0131\n" +
                "0.0132\t0.0133\t0.0134\t0.0135\n" +
                "0.0136\t0.0137\t0.0138\t0.0139\n" +
                "0.0140\t0.0141\t0.0142\t0.0143\n" +
                "0.0144\t0.0145\t0.0146\t0.0147\n" +
                "0.0148\t0.0149\t0.0150\t0.0151\n" +
                "0.0152\t0.0153\t0.0154\t0.0155\n" +
                "0.0156\t0.0157\t0.0158\t0.0159\n" +
                "0.0160\t0.0161\t0.0162\t0.0163\n" +
                "0.0164\t0.0165\t0.0166\t0.0167\n" +
                "0.0168\t0.0169\t0.0170\t0.0171\n" +
                "0.0172\t0.0173\t0.0174\t0.0175\n" +
                "0.0176\t0.0177\t0.0178\t0.0179\n" +
                "0.0180\t0.0181\t0.0182\t0.0183\n" +
                "0.0184\t0.0185\t0.0186\t0.0187\n" +
                "0.0188\t0.0189\t0.0190\t0.0191\n" +
                "0.0192\t0.0193\t0.0194\t0.0195\n" +
                "0.0196\t0.0197\t0.0198\t0.0199\n" +
                "0.0200\t0.0201\t0.0202\t0.0203\n" +
                "0.0204\t0.0205\t0.0206\t0.0207\n" +
                "0.0208\t0.0209\t0.0210\t0.0211\n" +
                "0.0212\t0.0213\t0.0214\t0.0215\n" +
                "0.0216\t0.0217\t0.0218\t0.0219\n" +
                "0.0220\t0.0221\t0.0222\t0.0223\n" +
                "0.0224\t0.0225\t0.0226\t0.0227\n" +
                "0.0228\t0.0229\t0.0230\t0.0231\n" +
                "0.0232\t0.0233\t0.0234\t0.0235\n" +
                "0.0236\t0.0237\t0.0238\t0.0239\n" +
                "0.0240\t0.0241\t0.0242\t0.0243\n" +
                "0.0244\t0.0245\t0.0246\t0.0247\n" +
                "0.0248\t0.0249\t0.0250\t0.0251\n" +
                "0.0252\t0.0253\t0.0254\t0.0255\n" +
                "0.0256\t0.0257\t0.0258\t0.0259\n" +
                "0.0260\t0.0261\t0.0262\t0.0263\n" +
                "0.0264\t0.0265\t0.0266\t0.0267\n" +
                "0.0268\t0.0269\t0.0270\t0.0271\n" +
                "0.0272\t0.0273\t0.0274\t0.0275\n" +
                "0.0276\t0.0277\t0.0278\t0.0279\n" +
                "0.0280\t0.0281\t0.0282\t0.0283\n" +
                "0.0284\t0.0285\t0.0286\t0.0287\n" +
                "0.0288\t0.0289\t0.0290\t0.0291\n" +
                "0.0292\t0.0293\t0.0294\t0.0295\n" +
                "0.0296\t0.0297\t0.0298\t0.0299\n" +
                "0.0300\t0.0301\t0.0302\t0.0303\n" +
                "0.0304\t0.0305\t0.0306\t0.0307\n" +
                "0.0308\t0.0309\t0.0310\t0.0311\n" +
                "0.0312\t0.0313\t0.0314\t0.0315\n" +
                "0.0316\t0.0317\t0.0318\t0.0319\n" +
                "0.0320\t0.0321\t0.0322\t0.0323\n" +
                "0.0324\t0.0325\t0.0326\t0.0327\n" +
                "0.0328\t0.0329\t0.0330\t0.0331\n" +
                "0.0332\t0.0333\t0.0334\t0.0335\n" +
                "0.0336\t0.0337\t0.0338\t0.0339\n" +
                "0.0340\t0.0341\t0.0342\t0.0343\n" +
                "0.0344\t0.0345\t0.0346\t0.0347\n" +
                "0.0348\t0.0349\t0.0350\t0.0351\n" +
                "0.0352\t0.0353\t0.0354\t0.0355\n" +
                "0.0356\t0.0357\t0.0358\t0.0359\n" +
                "0.0360\t0.0361\t0.0362\t0.0363\n" +
                "0.0364\t0.0365\t0.0366\t0.0367\n" +
                "0.0368\t0.0369\t0.0370\t0.0371\n" +
                "0.0372\t0.0373\t0.0374\t0.0375\n" +
                "0.0376\t0.0377\t0.0378\t0.0379\n" +
                "0.0380\t0.0381\t0.0382\t0.0383\n");
        Map<HMMState, Map<Triple<AminoAcid>, Double>> result = dummyMatchEmissionRepository.readOneBlock(blockScanner);

        double i = 0.0000;
        for (HMMState state : matchingStates) {
            for (AminoAcid firstAcid : aminoAcids) {
                for (AminoAcid secondAcid : aminoAcids) {
                    for (AminoAcid thirdAcid : aminoAcids) {
                        // A small delta is needed here because of precision loss
                        assertEquals(i, result.get(state).get(new Triple<>(firstAcid, secondAcid, thirdAcid)), 0.0000000000001);
                        i += 0.0001;
                    }
                }
            }
        }
    }
}