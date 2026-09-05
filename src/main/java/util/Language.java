package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

// Đọc 2 file lang/vi.txt và lang/en.txt (MÃ=câu đầy đủ), trả về câu theo ngôn ngữ đang chọn.
public class Language {
    public static final String VI = "VI";
    public static final String EN = "EN";

    // --- Mã UI (trùng tên trong lang/vi.txt và lang/en.txt) ---
    public static final String MENU_TITLE = "MENU_TITLE";
    public static final String MENU_ADD_DEPT = "MENU_ADD_DEPT";
    public static final String MENU_TEST = "MENU_TEST";
    public static final String MENU_LANGUAGE = "MENU_LANGUAGE";
    public static final String MENU_EXIT = "MENU_EXIT";
    public static final String PROMPT_CHOICE = "PROMPT_CHOICE";
    public static final String GOODBYE = "GOODBYE";
    public static final String ADD_DEPT_TITLE = "ADD_DEPT_TITLE";
    public static final String PROMPT_DEPT_ID = "PROMPT_DEPT_ID";
    public static final String PROMPT_DEPT_NAME = "PROMPT_DEPT_NAME";
    public static final String ADD_DEPT_SUCCESS = "ADD_DEPT_SUCCESS";
    public static final String TEST_TITLE = "TEST_TITLE";
    public static final String PROMPT_TEST_NAME = "PROMPT_TEST_NAME";
    public static final String LABEL_ENTERED_NAME = "LABEL_ENTERED_NAME";
    public static final String PROMPT_TEST_GENDER = "PROMPT_TEST_GENDER";
    public static final String LABEL_ENTERED_GENDER = "LABEL_ENTERED_GENDER";
    public static final String PROMPT_TEST_CONFIRM = "PROMPT_TEST_CONFIRM";
    public static final String LABEL_CONFIRMATION = "LABEL_CONFIRMATION";
    public static final String LABEL_ACCEPTED = "LABEL_ACCEPTED";
    public static final String LABEL_DECLINED = "LABEL_DECLINED";
    public static final String LABEL_DATE_FORMAT = "LABEL_DATE_FORMAT";
    public static final String TABLE_DEPT_ID = "TABLE_DEPT_ID";
    public static final String TABLE_DEPT_NAME = "TABLE_DEPT_NAME";
    public static final String TABLE_CREATE_DATE = "TABLE_CREATE_DATE";
    public static final String TABLE_UPDATE_DATE = "TABLE_UPDATE_DATE";
    public static final String LANG_TITLE = "LANG_TITLE";
    public static final String LANG_OPTION_VI = "LANG_OPTION_VI";
    public static final String LANG_OPTION_EN = "LANG_OPTION_EN";
    public static final String LANG_PROMPT = "LANG_PROMPT";
    public static final String LANG_SELECTED = "LANG_SELECTED";
    public static final String LANG_INVALID = "LANG_INVALID";

    // --- Mã lỗi (trùng tên trong file ngôn ngữ) ---
    public static final String UNKNOWN = "UNKNOWN";
    public static final String EMPTY_CHOICE = "EMPTY_CHOICE";
    public static final String INVALID_CHOICE = "INVALID_CHOICE";
    public static final String EMPTY_DEPT_ID = "EMPTY_DEPT_ID";
    public static final String DUPLICATE_DEPT_ID = "DUPLICATE_DEPT_ID";
    public static final String EMPTY_DEPT_NAME = "EMPTY_DEPT_NAME";
    public static final String ADD_DEPT_FAIL = "ADD_DEPT_FAIL";
    public static final String EMPTY_DEPT_LIST = "EMPTY_DEPT_LIST";
    public static final String EMPTY_INPUT = "EMPTY_INPUT";
    public static final String EMPTY_NAME = "EMPTY_NAME";
    public static final String EMPTY_GENDER = "EMPTY_GENDER";
    public static final String INVALID_GENDER = "INVALID_GENDER";
    public static final String EMPTY_CONFIRM = "EMPTY_CONFIRM";
    public static final String INVALID_CONFIRM = "INVALID_CONFIRM";
    public static final String FILE_IO = "FILE_IO";

    private static String current = VI; // Ngôn ngữ đang chọn: VI hoặc EN
    private static final Map<String, String> viMap = new HashMap<>(); // MÃ -> câu tiếng Việt
    private static final Map<String, String> enMap = new HashMap<>(); // MÃ -> câu tiếng Anh

    // Load 2 file ngôn ngữ một lần khi class được nạp
    static {
        loadFile(viMap, "vi.txt");
        loadFile(enMap, "en.txt");
    }

    // Trả về mã ngôn ngữ hiện tại ("VI" hoặc "EN")
    public static String getCurrent() {
        return current;
    }

    // true nếu đang dùng English
    public static boolean isEnglish() {
        return EN.equals(current);
    }

    // true nếu đang dùng Tiếng Việt
    public static boolean isVietnamese() {
        return VI.equals(current);
    }

    // Đặt ngôn ngữ; giá trị khác EN thì mặc định về VI
    public static void set(String lang) {
        current = EN.equalsIgnoreCase(lang) ? EN : VI;
    }

    // Lấy câu đầy đủ theo mã, dùng ngôn ngữ đang chọn
    public static String get(String code) {
        return getFrom(current, code);
    }

    // Lấy câu theo một ngôn ngữ cụ thể (dùng khi in song ngữ lúc chọn ngôn ngữ)
    public static String getFrom(String lang, String code) {
        if (code == null) {
            code = UNKNOWN;
        }
        Map<String, String> primary = EN.equals(lang) ? enMap : viMap;
        Map<String, String> fallback = EN.equals(lang) ? viMap : enMap;
        String msg = primary.get(code);
        if (msg != null) {
            return msg;
        }
        // Không có trong ngôn ngữ chính thì lấy ngôn ngữ còn lại
        msg = fallback.get(code);
        if (msg != null) {
            return msg;
        }
        String unknown = primary.get(UNKNOWN);
        return (unknown != null ? unknown : code) + " [" + code + "]";
    }

    // Lấy câu theo mã rồi ghép thêm chi tiết (ví dụ thông báo exception khi đọc file)
    public static String get(String code, String extra) {
        String msg = get(code);
        if (extra == null || extra.isBlank()) {
            return msg;
        }
        return msg + extra;
    }

    // Hiện menu chọn ngôn ngữ (luôn in song ngữ vì lúc này có thể chưa chọn)
    public static void choose() {
        while (true) {
            System.out.println("\n=== " + getFrom(VI, LANG_TITLE) + " / " + getFrom(EN, LANG_TITLE) + " ===");
            System.out.println("1. " + getFrom(VI, LANG_OPTION_VI));
            System.out.println("2. " + getFrom(EN, LANG_OPTION_EN));
            String choice = Validation.readLine(getFrom(VI, LANG_PROMPT) + " / " + getFrom(EN, LANG_PROMPT));
            if ("1".equals(choice) || "vi".equalsIgnoreCase(choice) || "vn".equalsIgnoreCase(choice)) {
                set(VI);
                System.out.println(get(LANG_SELECTED));
                return;
            }
            if ("2".equals(choice) || "en".equalsIgnoreCase(choice) || "english".equalsIgnoreCase(choice)) {
                set(EN);
                System.out.println(get(LANG_SELECTED));
                return;
            }
            System.out.println(getFrom(VI, LANG_INVALID) + " / " + getFrom(EN, LANG_INVALID));
        }
    }

    // Đọc file lang/*.txt: mỗi dòng MÃ=câu đầy đủ, bỏ dòng trống và comment #
    private static void loadFile(Map<String, String> map, String fileName) {
        try (InputStream in = openLangStream(fileName)) {
            if (in == null) {
                System.out.println("Không tìm thấy file ngôn ngữ: lang/" + fileName);
                return;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                int sep = line.indexOf('=');
                if (sep <= 0) {
                    continue;
                }
                String code = line.substring(0, sep).trim();
                String text = line.substring(sep + 1);
                map.put(code, text);
            }
        } catch (Exception e) {
            System.out.println("Lỗi đọc file ngôn ngữ " + fileName + ": " + e.getMessage());
        }
    }

    // Ưu tiên stream trong jar (classpath), không có thì mở file trên đĩa
    private static InputStream openLangStream(String fileName) throws Exception {
        InputStream in = Language.class.getResourceAsStream("/lang/" + fileName);
        if (in != null) {
            return in;
        }
        in = Language.class.getClassLoader().getResourceAsStream("lang/" + fileName);
        if (in != null) {
            return in;
        }
        File file = findLangFile(fileName);
        return file != null ? new FileInputStream(file) : null;
    }

    // Tìm lang/vi.txt cạnh thư mục chạy, hoặc trong src/main/resources khi đang code
    private static File findLangFile(String fileName) {
        File[] candidates = {
                new File("lang", fileName),
                new File("src/main/resources/lang", fileName)
        };
        for (File file : candidates) {
            if (file.isFile()) {
                return file;
            }
        }
        return null;
    }
}
