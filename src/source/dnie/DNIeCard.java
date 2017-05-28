package source.dnie;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardException;
import javax.smartcardio.CardNotPresentException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.TerminalFactory;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.util.ASN1Dump;

public class DNIeCard {

	 //Root folder selection command APDU
    private static final byte[] baCommandAPDURoot = {(byte) 0x00, (byte) 0xA4, (byte) 0x00, (byte) 0x00,
        (byte) 0x02, (byte) 0x50, (byte) 0x15};
    //CDF file selection command APDU
    private static final byte[] baCommandAPDUCDF = {(byte) 0x00, (byte) 0xA4, (byte) 0x00, (byte) 0x00,
        (byte) 0x02, (byte) 0x60, (byte) 0x04};
    //Read raw bytes from public data command
    private static final byte[] baCommandAPDU_BIN_RD_255_1 = {(byte) 0x00, (byte) 0xB0, (byte) 0x00, (byte) 0x00, (byte) 0xFF};
    private static final byte[] baCommandAPDU_BIN_RD_255_2 = {(byte) 0x00, (byte) 0xB0, (byte) 0x00, (byte) 0xFF, (byte) 0xFF};
    private static final byte[] baCommandAPDU_BIN_RD_255_3 = {(byte) 0x00, (byte) 0xB0, (byte) 0x01, (byte) 0xFE, (byte) 0xFF};
    private static final byte[] baCommandAPDU_BIN_RD_255_4 = {(byte) 0x00, (byte) 0xB0, (byte) 0x02, (byte) 0xFD, (byte) 0xFF};
    private static final byte[] baCommandAPDU_BIN_RD_255_5 = {(byte) 0x00, (byte) 0xB0, (byte) 0x03, (byte) 0xFC, (byte) 0xFF};
    private static final byte[] baCommandAPDU_BIN_RD_255_6 = {(byte) 0x00, (byte) 0xB0, (byte) 0x04, (byte) 0xFB, (byte) 0xFF};
    public static final int DNI_LENGTH = 9;
    private Card card;

    public void connect() throws CardException {
        try {
            List<CardTerminal> terminals = getTerminals();
            CardTerminal cardTerminal = getCardTerminal(terminals);
            card = getCard(cardTerminal);

        } catch (CardException ex) {
            throw new CardException(ex.getMessage());
        }


    }

    public void disconnect() throws CardException {
        if (card != null) {
            card.disconnect(true);
            //To avoid corrrupted data in the card
            card = null;
        }
    }

    private CardTerminal getCardTerminal(List<CardTerminal> terminals)
            throws CardException {
        if (terminals.isEmpty()) {
            throw new CardException("No hay perif�ricos conectados");
        } else {
            return terminals.get(0);
        }
    }

    private List<CardTerminal> getTerminals() throws CardException {
        List<CardTerminal> terminals;

        try {
            TerminalFactory factory = TerminalFactory.getDefault();
            terminals = factory.terminals().list();

        } catch (CardException ex) {
            throw new CardException("No hay perif�ricos conectados");
        }

        return terminals;
    }

    private Card getCard(CardTerminal cardTerminal)
            throws CardException {
        Card connectedCard = null;
        if (cardTerminal == null) {
            throw new CardException("No hay lectores conectados");

        } else {
            if (!cardTerminal.isCardPresent()) {
                throw new CardNotPresentException("No se encontr� ning�n DNIe");
            } else {
                connectedCard = cardTerminal.connect("*");
            }
        }

        return connectedCard;
    }

    public String readPublicData() throws CardException, IOException {
        String publicData = "";
        
        if (null != card){            
            CardChannel channel = card.getBasicChannel();
            sendRootFileFirstCommands(channel);
            ByteArrayOutputStream response = new ByteArrayOutputStream();

            byte[] subResponse1 = sendCommandAPDU(channel, baCommandAPDU_BIN_RD_255_1);
            addFirstToSecond(subResponse1, response);
            byte[] subResponse2 = sendCommandAPDU(channel, baCommandAPDU_BIN_RD_255_2);
            addFirstToSecond(subResponse2, response);
            byte[] subResponse3 = sendCommandAPDU(channel, baCommandAPDU_BIN_RD_255_3);
            addFirstToSecond(subResponse3, response);
            byte[] subResponse4 = sendCommandAPDU(channel, baCommandAPDU_BIN_RD_255_4);
            addFirstToSecond(subResponse4, response);
            byte[] subResponse5 = sendCommandAPDU(channel, baCommandAPDU_BIN_RD_255_5);
            addFirstToSecond(subResponse5, response);
            byte[] subResponse6 = sendCommandAPDU(channel, baCommandAPDU_BIN_RD_255_6);
            addFirstToSecond(subResponse6, response);

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(response.toByteArray());
            ASN1InputStream inputStream = new ASN1InputStream(byteArrayInputStream);
            ASN1Primitive objectRead = inputStream.readObject();

            if (objectRead != null) {
                publicData = ASN1Dump.dumpAsString(objectRead);
            } else {
                throw new CardException("No hay datos disponibles en el DNIe."
                        + "\nRetire y vuelva a insertar el DNI-e.");
            }         
        }
        else{
            throw new CardException("No se encuentra la tarjeta");
        }
        System.out.println("CLASE DNIeCard : \n"+publicData);
        return publicData;
    }

    private void sendRootFileFirstCommands(CardChannel channel)
            throws CardException {

        channel.transmit(new CommandAPDU(baCommandAPDURoot));
        channel.transmit(new CommandAPDU(baCommandAPDUCDF));
    }

    private byte[] sendCommandAPDU(CardChannel channel, byte[] command)
            throws CardException {

        return channel.transmit(new CommandAPDU(command)).getBytes();

    }

    private void addFirstToSecond(byte[] first, ByteArrayOutputStream second) {
        for (int i = 0; i < first.length - 2; ++i) {
            second.write(first[i]);
        }
    }
}
