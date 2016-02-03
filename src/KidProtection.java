/**
 * Created by tomgiraudet on 01/02/16.
 */
public class KidProtection {

    final static String[] BAD_WORDS = {"Spongebob", "norrköping", "britney spears", "android", "tom", "apple"};

    public boolean analyze(String _content){
        for(int i = 0; i<BAD_WORDS.length; i++){
            if(_content.contains(BAD_WORDS[i])){
                return false;
            }
        }
        return true;
    }

}
