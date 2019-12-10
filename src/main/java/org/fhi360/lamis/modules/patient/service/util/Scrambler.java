/**
 *
 * @author aalozie
 */
package org.fhi360.lamis.modules.patient.service.util;
public class Scrambler {

    public String scrambleCharacters(String string) {
        if(string == null) return "";
        string = string.trim().toLowerCase();
        string = string.replace("a", "^");
        string = string.replace("b", "~");
        string = string.replace("c", "`");
        string = string.replace("e", "*");
        string = string.replace("f", "$");
        string = string.replace("g", "#");
        string = string.replace("h", "@");
        string = string.replace("i", "!");
        string = string.replace("j", "%");
        string = string.replace("k", "|");
        string = string.replace("n", "}");
        string = string.replace("o", "{");
        string = string.replace("'", "");
        return string;
    }

    public static String unscrambleCharacters(String string) {
        if(string == null) return "";
        string = string.replace("^", "a");
        string = string.replace("~", "b");
        string = string.replace("`", "c");
        string = string.replace("*", "e");
        string = string.replace("$", "f");
        string = string.replace("#", "g");
        string = string.replace("@", "h");
        string = string.replace("!", "i");
        string = string.replace("%", "j");
        string = string.replace("|", "k");
        string = string.replace("}", "n");
        string = string.replace("{", "o");
        return string;
    }

    public String scrambleNumbers(String string) {
        if(string == null) return "";
        string = string.replace("1", "^");
        string = string.replace("2", "~");
        string = string.replace("3", "`");
        string = string.replace("5", "*");
        string = string.replace("6", "$");
        string = string.replace("7", "#");
        string = string.replace("8", "@");
        string = string.replace("9", "!");
        return string;
    }

    public String unscrambleNumbers(String string) {
        if(string == null) return "";
        string = string.replace("^", "1");
        string = string.replace("~", "2");
        string = string.replace("`", "3");
        string = string.replace("*", "5");
        string = string.replace("$", "6");
        string = string.replace("#", "7");
        string = string.replace("@", "8");
        string = string.replace("!", "9");
        return string;
    }
}
