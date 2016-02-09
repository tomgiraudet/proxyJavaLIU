/**
 * Created by tomgiraudet on 01/02/16.
 */
public class KidProtection {

    final static String[] BAD_WORDS = {"SpongeBob", "norrköping", "Norrköping", "britney spears"};

    public boolean analyze(String _content){
        System.out.println("[KidProtection] Request for checking content received");
        System.out.println("[KidProtection] Looking for bad words ...");
        for(int i = 0; i<BAD_WORDS.length; i++){
            if(_content.contains(BAD_WORDS[i])){
                System.out.println("[KidProtection] Bad words spotted ! : "+ BAD_WORDS[i]);
                return true;
            }
        }
        System.out.println("[KidProtection] Content safe");
        return false;
    }

}
