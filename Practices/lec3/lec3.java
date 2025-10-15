import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class lec3 {
    /**
     * 题1：对字符串列表进行排序：先按长度升序；长度相同按字母表顺序（不区分大小写）；
     * 若仍相同则按原始字典序保证稳定性。
     *
     * 不修改传入的列表，返回排序后的新列表。
     */
    public static List<String> sortStringsByRules(List<String> input) {
        if (input == null || input.isEmpty()) {
            return new ArrayList<>();
        }
        List<String> result = new ArrayList<>(input);
        result.sort(
                Comparator.comparingInt(String::length)
                        .thenComparing(String.CASE_INSENSITIVE_ORDER)
                        .thenComparing(Comparator.naturalOrder()));
        return result;
    }

    /**
     * 题2：统计字符串中出现过的字符（大小写合并），
     * 按出现次数降序排序；若次数相同按字符升序。
     * 返回按规则排序后的字符列表（均为小写）。
     */
    public static List<Character> charactersByFrequency(String s) {
        List<Character> orderedCharacters = new ArrayList<>();
        if (s == null || s.isEmpty()) {
            return orderedCharacters;
        }

        Map<Character, Integer> counts = new HashMap<>();
        for (char ch : s.toCharArray()) {
            char key = ch;
            counts.put(key, counts.getOrDefault(key, 0) + 1);
        }

        orderedCharacters = new ArrayList<>(counts.keySet());
        orderedCharacters.sort((a, b) -> {
            int cmp = Integer.compare(counts.get(b), counts.get(a));
            if (cmp != 0)
                return cmp;
            return Character.compare(a, b);
        });

        return orderedCharacters;
    }
}