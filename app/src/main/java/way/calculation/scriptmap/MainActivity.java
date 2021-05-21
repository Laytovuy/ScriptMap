package way.calculation.scriptmap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;

import java.util.Locale;

public class MainActivity extends MainData { // TODO Відповідає за вміст головного меню гри.

    @Override
    protected void onCreate(Bundle savedInstanceState) { // TODO Цей клас, викликається на початку здійненя коду, тому в ньому виповниться все в першу чергу.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainDataExchange = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        button_clear = findViewById(R.id.button_clear);
        button_battleline = findViewById(R.id.button_battleline);
        button_player = findViewById(R.id.button_player);
        button_accident = findViewById(R.id.button_accident);
        button_stiffLeft = findViewById(R.id.button_stiffLeft);
        button_count = findViewById(R.id.button_count);
        button_stiffRight = findViewById(R.id.button_stiffRight);
        button_modes = findViewById(R.id.button_modes);
        button_notcount = findViewById(R.id.button_notcount);
        button_negative = findViewById(R.id.button_negative);
        button_positive = findViewById(R.id.button_positive);
        button_action = findViewById(R.id.button_action);
        button_direction = findViewById(R.id.button_direction);

        linear_Right_num_change = findViewById(R.id.linear_Right_num_change);
        linear_Center_num_change = findViewById(R.id.linear_Center_num_change);
        linear_Left_num_change = findViewById(R.id.linear_Left_num_change);
        linear_Left_Center_Right = findViewById(R.id.linear_Left_Center_Right);
        linear_count_choice = findViewById(R.id.linear_count_choice);
        linear_modes_count_choice = findViewById(R.id.linear_modes_count_choice);
        linear_accident_player = findViewById(R.id.linear_accident_player);

        textView_stiffcount = findViewById(R.id.textView_stiffcount);

        left_direct_center = findViewById(R.id.Left_direction_Center);
        left_direct_right = findViewById(R.id.Left_direction_Right);
        center_direct_right = findViewById(R.id.Center_direction_Right);
        left_direct_center.setOnLongClickListener(long_direction_choice);
        left_direct_right.setOnLongClickListener(long_direction_choice);
        center_direct_right.setOnLongClickListener(long_direction_choice);

        left_result_center = findViewById(R.id.Left_result_Center);
        left_result_right = findViewById(R.id.Left_result_Right);
        center_result_right = findViewById(R.id.Center_result_Right);

        launcher_background = findViewById(R.id.launcher_background);
        launcher_background.setOnLongClickListener(long_click_launcher_background);
        button_clear.setOnLongClickListener(long_click_clear);

        editText_left = findViewById(R.id.Left_EditText);
        editText_center = findViewById(R.id.Center_EditText);
        editText_right = findViewById(R.id.Right_EditText);
        editText_left.addTextChangedListener(editText_watcher);
        editText_center.addTextChangedListener(editText_watcher);
        editText_right.addTextChangedListener(editText_watcher);

        arguments = getIntent().getExtras();
        if(arguments != null){ setRotationAnimation(); outData(); getIntent().removeExtra("intent"); getIntent().removeCategory("intent"); arguments = getIntent().getExtras(); }
    } // І кстаті, цей код має послідовність, тобто функціонал вюшок розпосаний, від нижньої вшки до верхньої, йдучи зік заком, з ліва на право. І між методами, пропуски в два рядка.


    private void setRotationAnimation() { // TODO Проводить заміну анімації зміни орінтації екрана.
        int rotationAnimation = WindowManager.LayoutParams.ROTATION_ANIMATION_JUMPCUT;
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        winParams.rotationAnimation = rotationAnimation;
        win.setAttributes(winParams);
    }

    public void localization (String languageCode) { // TODO Проводить зміну локалізації.
        Locale locale = new Locale(languageCode); // TODO Локалізація погано працює
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(config, null);

        // TODO Є бажння придумати щось краще. Згрупувати це.
        button_clear.setText(R.string.clear);
        if (p == 3) {button_player.setText(R.string.outline);} else {button_player.setText(R.string.addline);}
        button_action.setText(R.string.action);
    }


    private final TextWatcher editText_watcher = new TextWatcher() { // TODO Це реєструє редагування ЕдітТекста користувачем.
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {editText_check();} // Викликає функцію яка провірить чи всі заповнені поля.
        @Override
        public void afterTextChanged(Editable s) {}
    };


    View.OnLongClickListener long_click_launcher_background = v -> { // TODO Це автоматичне виставлення в поля 50 очків
        if (menu_on){
            button_clear.callOnClick();
            editText_right.setText(String.valueOf(50));
            if (p == 3){editText_center.setText(String.valueOf(50));}
            editText_left.setText(String.valueOf(50));
        }
        return false;
    };

    public void click_launcher_background (View view){ if (menu_on) { if (m == 4 && m_on) {m = -4;} close_cursor();} } // TODO В цей метод зруповані закриття меню, і забрання курсора.


    public void click_clear (View view){ // TODO Очищує значення, і ставить їх в дефолтне положення.
        if (menu_on) {
            L_direct_R = 0; L_result_R = 0;
            editText_right.setText(""); editText_left.setText("");
            if (p == 3 || system_clear_time + 300 > System.currentTimeMillis()) { // Очищення для 3 гравців. І тут реалізоване локальне очищення,
                // тобто скриті ЕдітПоля не стираються, тому код після "||" = "або", для того щоб, нажавши в промижутку часу два рази на кнопку очищення, стерти і не видемі.
                L_direct_C = 0; C_direct_R = 0;
                L_result_C = 0;  C_result_R = 0;
                editText_center.setText("");
            }

            system_clear_time = System.currentTimeMillis(); // При першому клікови фіксується час нажаття. При повторному нажаті, провірка на час є вище, тому
            // тому провіриться старий записаний час, до якого додадутся секунди, що порівняються з теперішнім, і як що теперішній більший, то гравець нажав пізніше.
            result_choice(); direction_choice(); close_cursor(); // Відключає відображення поля результату. + Це визови методів відображення напрямку, і закритя курсора.
        }
    }

    View.OnLongClickListener long_click_clear = v -> { // TODO При втримані на кнопку клір, скидуються кнопки на дефолт, як і режими, але не значення полей.
        if (!menu_on) {
            menu_on = true; menu_visible(); result_choice();
            if (string_left.equals("0")){editText_left.setText("");}
            if (string_center.equals("0") && p == 3){editText_center.setText("");}
            if (string_right.equals("0")){ editText_right.setText("");}
        } else {
            if (languageCode.equals("default")) {
                languageCode  = "uk";
            } else {languageCode = "default";}

            localization(languageCode);
            m = 0; button_modes.setText(R.string.modes);
            c = 0; button_count.setText(R.string.count);
            sc = 10; view_on = false; viwe_on_off(); close_cursor();
        }
        return true; // "true" відповідає за те, що цей код не визве короткий клік, при натискані.
    };


    public void click_player (View view){ // TODO Це клік по додаваню гравців.
        if (arguments == null) {p ++; if (p == 4) {p = 2;}} // По дефолту значення 2, тому що початковий режим для двох гравців

        if (p == 2){ // Зміна на 4 гравців, який насправді перехід до 2
            linear_Left_Center_Right.setVisibility(View.GONE);
            editText_center.setVisibility(View.GONE);
            button_player.setText(R.string.addline);
            result_choice();
        }

        if (p == 3){ // Зміна на 3 гравців
            linear_Left_Center_Right.setVisibility(View.VISIBLE);
            editText_center.setVisibility(View.VISIBLE);
            button_player.setText(R.string.outline);
            result_choice();
        }

        if (arguments == null) { editText_check(); close_cursor();}
        // Це визови методів провірки на написаний текс в полях(просто кнопка повина бути виключена, як що лише двоє гравців
    } // заповнені, і повина бути включна, як що текс є і в третій, яка тепер не скрита), і закритя курсора.


    public void click_count (View view){ // TODO Відповідає за жорсткосткість атак.
        int id = view.getId(); // Не величка привязка до одного слухача нажаття, яка фіксує по айді, в одній системі.
        if (id == R.id.button_stiffLeft) { // Ліва кнопка. Більше число видасть менший результат. Зявляються після включення меню.
            sc += 2; if (sc > 20){ sc = 10; }
        } else if (id == R.id.button_count) { // Нажаття при закритому меню вибора, змінює цю кнопку до маленьких розмірів, і включає бокові кнопки.
            if (!c_on){ // Як що меню не відкрите.
                c_on = true; view_on = true; viwe_on_off(); // Viwe_on_off відповідає за виключення постороніх кнопок, під час користування каунт меню.
                button_modes.setVisibility(View.GONE);
                button_count.getLayoutParams().width = (int)
                        (button_count.getResources().getDisplayMetrics().density * 28); // Зменшує кнопку, щоб помістити інші кнопки "вправо" і "вліво", не змінюючи дизайн в 100 діпі ширини кнопки.

                button_count.setText(""); // Ми зменшили кнопку, тому її текс ми очищуємо. Він поставиться вже при закреті. І іншим методом.
                linear_Right_num_change.setVisibility(View.VISIBLE);
                if (p == 3) { linear_Center_num_change.setVisibility(View.VISIBLE);}
                linear_Left_num_change.setVisibility(View.VISIBLE);
                textView_stiffcount.setVisibility(View.VISIBLE); // Цей текс накладений поверх кнопок, щоб вітображатись як цільний.
                linear_count_choice.setVisibility(View.VISIBLE);
                button_stiffLeft.setVisibility(View.VISIBLE); // Це ліва кнопка показана
                button_stiffRight.setVisibility(View.VISIBLE); // Це права кнопка показана

                if (m == 2 || m == 3) { // Ця функція відповідає за відображення, кнопки, що дає змогу припинити присвоєня результатів до ЕдітПолей.
                    button_notcount.setVisibility(View.VISIBLE);
                    button_notcount.setTextSize(8);
                }
            } else {sc = 10; textView_stiffcount.setText(Html.fromHtml(getString(R.string.stiffprimary)));} // Як що включене меню вибора, то нажаття
            // по зменшеній кнопці скидує значення жорсткості рахування.
        } else if (id == R.id.button_stiffRight) { // Права кнопка. Менше число видасть більший результат. Зявляються після включення меню.
            sc -= 2; if (sc < 0){ sc = 10; }
        }

        if (c_on) { // Як шо меню відкрите. Це код відповідає за відображення жорсткості.
            if (sc == 0){
                textView_stiffcount.setText(R.string.vabank);
                textView_stiffcount.setTextSize(14);
            } else if (sc == 20) {
                textView_stiffcount.setText(R.string.humbly);
                textView_stiffcount.setTextSize(14);
            } else {
                if (sc == 10) {
                    textView_stiffcount.setText(Html.fromHtml(getString(R.string.stiffprimary)));
                } else {
                    textView_stiffcount.setText(Html.fromHtml(getString(R.string.stiffness, sc + 2, sc, sc - 2)));
                }
                textView_stiffcount.setTextSize(12);
            }
        }
    }

    public void click_count_choice (View view) { // TODO Вибір методу рахування.
        int id = view.getId();
        if (id == R.id.button_notcount) {
            c = 3; button_count.setText(R.string.notcount);
        } else if (id == R.id.button_negative) {
            c = 2; button_count.setText(R.string.negative);
        } else if (id == R.id.button_positive) {
            c = 1; button_count.setText(R.string.positive);
        }
        close_cursor (); // Щоб відключити відображення курсору, я прибіг по простому шляху, я віключаю і включаю ЕдітТексти, і це змінює відобрежання курсору на фолс.
    }

    public void click_count_number (View view) {
        if (c_on) { int id = view.getId();

            if (id == R.id.Right_num_plus || id == R.id.Right_num_minus) {if (!string_right.isEmpty()) { Int_right = Integer.parseInt(string_right); } else {Int_right = 0;}}
            if (id == R.id.Right_num_plus)   { Int_right++; editText_right.setText(String.valueOf(Int_right));
            } else if (id == R.id.Right_num_minus)  { if (Int_right > 1) {Int_right--; editText_right.setText(String.valueOf(Int_right));} else {editText_right.setText("");} }

            if (id == R.id.Center_num_plus || id == R.id.Center_num_minus) {if (!string_center.isEmpty()) { Int_center = Integer.parseInt(string_center); } else {Int_center = 0;}}
            if (id == R.id.Center_num_plus)  { Int_center++; editText_center.setText(String.valueOf(Int_center));
            } else if (id == R.id.Center_num_minus) { if (Int_center > 1) {Int_center--; editText_center.setText(String.valueOf(Int_center));} else {editText_center.setText("");} }

            if (id == R.id.Left_num_plus || id == R.id.Left_num_minus) {if (!string_left.isEmpty()) { Int_left = Integer.parseInt(string_left); } else {Int_left = 0;}}
            if (id == R.id.Left_num_plus)    { Int_left++; editText_left.setText(String.valueOf(Int_left));
            } else if (id == R.id.Left_num_minus)   { if (Int_left > 1) {Int_left--; editText_left.setText(String.valueOf(Int_left));} else {editText_left.setText("");} }
        }
    }


    public void click_modes (View view) { // TODO Вибір режиму
        if (!m_on){
            m_on = true; view_on = true; viwe_on_off(); // viwe_on_off запускает метод, який відключає кнопки на час взамодії з меню.
            button_direction.setVisibility(View.VISIBLE);
            button_accident.setVisibility(View.VISIBLE);
            button_battleline.setVisibility(View.VISIBLE);
            click_mode_choice();
        }  else {
            int id = view.getId();
            if (id == R.id.button_modes) { // Вибір режима вероідність.
                if (m == 0){m = 1;}
            } else if (id == R.id.button_direction) { // Вибір режима напрямок.
                if (m != 2){m = 2;} else {m = 1;} // Як що гравець вибирає знову режим в якому знаходиться, від змінюється на режим ймовірно.
            } else if (id == R.id.button_accident) { // Вибір режима випадок.
                if (m != 3){m = 3;} else {m = 1;}
            } else if (id == R.id.button_battleline) { // Вибір режима лініябою.
                if (m != 4){m = 4;} else {m = 1;}
            }

            if (m == 4) { button_modes.setEnabled(false);
                final Handler handler = new Handler(); // Цей метод створює затримку, для того щоб пройшла анімація, закривання меню.
                handler.postDelayed(() -> {
                    setRotationAnimation();
                    Intent intent = new Intent(MainActivity.this, MainBattleline.class); // Це створення обєкту який містить в собі запуск Активиті.
                    saveData(); intent.putExtra("intent", "intent");
                    startActivity(intent);
                }, 250); }
            if (m == 1 && c == 3){c = 0;} // Задумка в тому, що "не рахувати" дозволяє використати режим "випадковість", і прораховувати як монетку, без зачислення результатів.
            close_cursor();
        }
    }


    public void click_mode_choice (){ // TODO Меню вибору режимів

        if (m == 1 || m == 0){ // Віалізує режим probably. Дефолтний режем 0, але при відкреті я вітображаю меню 1.
            button_direction.setText(R.string.direction);
            button_modes.setText(R.string.probably);
            button_accident.setText(R.string.accident);
            button_battleline.setText(R.string.battleline);
        }

        if (m == 2){ // Віалізує режим direction.
            button_direction.setText(R.string.probably); // Режим probably замінює своїм текстом вибрану кнопку, і далі нажавши на підмінений текст ми зрівняємо теперній режим і під кнопкою.
            button_modes.setText(R.string.direction); // І як що вони схожі ми просто змінемо на дефолтний probably.
            button_accident.setText(R.string.accident);
            button_battleline.setText(R.string.battleline);
        }

        if (m == 3){ // Віалізує режим accident.
            button_direction.setText(R.string.direction);
            button_modes.setText(R.string.accident);
            button_accident.setText(R.string.probably);
            button_battleline.setText(R.string.battleline);
        }

        if (m == 4){ // Віалізує режим battleline.
            button_direction.setText(R.string.direction);
            button_modes.setText(R.string.battleline);
            button_accident.setText(R.string.accident);
            button_battleline.setText(R.string.probably);
        }
    }


    public void click_action (View view){ // TODO Цей метод викликається після кліку на Action, відповідає за надіслання даних в клас, і подальшуї оброботку.
        if (m == 0 || m == 4 || c == 0 || m == 2 && menu_on && (
                ((L_direct_C == 0 || L_direct_R == 0 || C_direct_R == 0) && p == 3) || L_direct_R == 0 && p == 2)){
            if (m == 0 || m == 4){button_modes.setEnabled(false);}
            if (c == 0){button_count.setEnabled(false);}
            if (m == 2){
                if (L_direct_R == 0) { left_direct_right.setVisibility(View.INVISIBLE); }
                if (p == 3){
                    if (L_direct_C == 0) { left_direct_center.setVisibility(View.INVISIBLE); }
                    if (C_direct_R == 0) { center_direct_right.setVisibility(View.INVISIBLE); }
                }
            }

            final Handler handler = new Handler();
            handler.postDelayed(() -> {
                close_cursor();
                if ((m == 0 || m == 4) && !c_on){button_modes.setEnabled(true);}
                if (c == 0 && !m_on){button_count.setEnabled(true);}
                if (m == 2){
                    left_direct_right.setVisibility(View.VISIBLE);
                    left_direct_center.setVisibility(View.VISIBLE);
                    center_direct_right.setVisibility(View.VISIBLE);
                }
            }, 250);
        } else {
            if (!string_left.isEmpty()) { Int_left = Integer.parseInt(string_left); }
            if (!string_center.isEmpty() && p != 2) { Int_center = Integer.parseInt(string_center); }
            if (!string_right.isEmpty()) { Int_right = Integer.parseInt(string_right); }

            /*if (p == 3){ Int_left = Integer.parseInt(string_left); Int_center = Integer.parseInt(string_center); Int_right = Integer.parseInt(string_right);
            } else if (p == 2) { Int_left = Integer.parseInt(string_left); Int_right = Integer.parseInt(string_right); }*/


            if ((Int_left != 0 && Int_right != 0 && p == 2) || (Int_left != 0 && Int_center != 0  && Int_right != 0 && p == 3 && menu_on) ||
                    (Int_left != 0 && Int_center != 0 || Int_left != 0 && Int_right != 0 || Int_right != 0 && Int_center != 0) && p == 3  && !menu_on) {

                if (!menu_on) {
                    final ActionCalculator DataExchange = new ActionCalculator();

                    DataExchange.Int_left = Int_left; if (p != 2) { DataExchange.Int_center = Int_center; } DataExchange.Int_right = Int_right;
                    DataExchange.m = m; DataExchange.c = c; DataExchange.p = p; DataExchange.sc = sc;
                    if (p != 2) { DataExchange.L_direct_C = L_direct_C; } DataExchange.L_direct_R = L_direct_R; if (p != 2) { DataExchange.C_direct_R = C_direct_R; }

                    if (Int_left + Int_center + Int_right != 0) { DataExchange.Calculation(); }

                    Final_left = DataExchange.Final_left; if (p != 2) { Final_center = DataExchange.Final_center; } Final_right = DataExchange.Final_right;
                    L_result_C = DataExchange.L_result_C; L_result_R = DataExchange.L_result_R; C_result_R = DataExchange.C_result_R;
                    if (p != 2) { L_direct_C = DataExchange.L_direct_C; } L_direct_R = DataExchange.L_direct_R; if (p != 2) { C_direct_R = DataExchange.C_direct_R; }

                    if ((Final_left == 0 && p == 2) || (Final_left == 0 && p == 3 && (Final_center == 0 || Final_right == 0))) { editText_left.setText("");}
                    else if (p != 1) { editText_left.setText(String.valueOf(Final_left)); }
                    if (Final_center == 0 && p == 3 && (Final_left == 0 || Final_right == 0)) { editText_center.setText("");}
                    else if (p != 2) { editText_center.setText(String.valueOf(Final_center)); }
                    if ((Final_right == 0 && p == 2) || (Final_right == 0 && p == 3 && (Final_left == 0 || Final_center == 0))) {  editText_right.setText("");}
                    else if (p != 1) { editText_right.setText(String.valueOf(Final_right)); }

                    result_choice(); direction_choice();
                } else {menu_on = false; menu_visible();}
            } else {
                close_cursor();
                if (Int_left == 0){editText_left.setText("");}
                if (Int_center == 0 && p == 3){editText_center.setText("");}
                if (Int_right == 0){ editText_right.setText("");}
            }
        }
    }


    public void click_direction_choice (View view) {
        int id = view.getId();
        if (id == R.id.Left_direction_Center) {
            if (m == 2){ L_direct_C++;}
            if (m == 2 && p == 2 && L_direct_C == 3){ L_direct_C = 1;}
            if (m == 2 && L_direct_C >= 4){ L_direct_C = 1;}

            if (m != 2 && p == 3 && L_direct_C != 3){L_direct_C = 3;}
            else if (m != 2 && L_direct_C == 3){ L_direct_C = 0;}
        } else if (id == R.id.Left_direction_Right){ System.out.println("Yes");
            if (m == 2) {L_direct_R++;}
            if (m == 2 && p == 2 && L_direct_R == 3) {L_direct_R = 1;}
            if (m == 2 && L_direct_R >= 4) {L_direct_R = 1;}

            if (m != 2 && p == 3 && L_direct_R != 3) {L_direct_R = 3;}
            else if (m != 2 && L_direct_R == 3) {L_direct_R = 0;}
        } else if (id == R.id.Center_direction_Right) {
            if (m == 2){ C_direct_R++;}
            if (m == 2 && p == 2 && C_direct_R == 3){ C_direct_R = 1;}
            if (m == 2 && C_direct_R >= 4){ C_direct_R = 1;}

            if (m != 2 && p == 3 && C_direct_R != 3){C_direct_R = 3;}
            else if (m != 2 && C_direct_R == 3){ C_direct_R = 0;}
        } direction_choice();
    }

    public void direction_choice() {
        if (L_direct_C == 0 || L_direct_C == 3){
            left_direct_center.setProgressDrawable(ContextCompat.getDrawable(this, R.drawable.seekbar_default)); }
        if (L_direct_C == 1 || L_direct_C == 2) { left_direct_center.setProgress(1);
            if ((Final_left == 0 || Final_center == 0) && L_result_C != 0) {
                left_direct_center.setProgressDrawable(ContextCompat.getDrawable(this, R.drawable.seekbar_fatality));
            } else { left_direct_center.setProgressDrawable(ContextCompat.getDrawable(this, R.drawable.seekbar_action_red)); } }
        if (L_direct_C == 0){ left_direct_center.setProgress(0);} // Left_and_Center
        if (L_direct_C == 1){ left_direct_center.setRotation(-90);} // Left_to_Center
        if (L_direct_C == 2){ left_direct_center.setRotation(90);} // Center_to_Left
        if (L_direct_C == 3 && p == 3){ left_direct_center.setProgress(30);} // Center_peace_Left

        if (L_direct_R == 0 || L_direct_R == 3){
            left_direct_right.setProgressDrawable(ContextCompat.getDrawable(this, R.drawable.seekbar_default)); }
        if (L_direct_R == 1 || L_direct_R == 2) { left_direct_right.setProgress(1);
            if ((Final_left == 0 || Final_right == 0) && L_result_R != 0) {
                left_direct_right.setProgressDrawable(ContextCompat.getDrawable(this, R.drawable.seekbar_fatality));
                } else { left_direct_right.setProgressDrawable(ContextCompat.getDrawable(this, R.drawable.seekbar_action_red)); } }
        if (L_direct_R == 0){ left_direct_right.setProgress(0); } // Left_and_Right
        if (L_direct_R == 2){ left_direct_right.setRotation(-90);} // Left_to_Right
        if (L_direct_R == 1){ left_direct_right.setRotation(90); } // Right_to_Left
        if (L_direct_R == 3){ left_direct_right.setProgress(30); } // Left_peace_Right

        if (C_direct_R == 0 || C_direct_R == 3){
            center_direct_right.setProgressDrawable(ContextCompat.getDrawable(this, R.drawable.seekbar_default)); }
        if (C_direct_R == 1 || C_direct_R == 2) { center_direct_right.setProgress(1);
            if ((Final_center == 0 || Final_right == 0) && C_result_R != 0) {
                center_direct_right.setProgressDrawable(ContextCompat.getDrawable(this, R.drawable.seekbar_fatality));
            } else { center_direct_right.setProgressDrawable(ContextCompat.getDrawable(this, R.drawable.seekbar_action_red)); } }
        if (C_direct_R == 0){ center_direct_right.setProgress(0);} // Center_and_Right
        if (C_direct_R == 1){ center_direct_right.setRotation(-90);} // Center_to_Right
        if (C_direct_R == 2){ center_direct_right.setRotation(90);} // Right_to_Center
        if (C_direct_R == 3 && p == 3){ center_direct_right.setProgress(30);} // Center_peace_Right
    }

    View.OnLongClickListener long_direction_choice = v -> {
        if (m == 2){ // Це відповідає за стерання напрямків, як що гравець захоче очистити лише їх.
            L_direct_R = 0;
            if (p == 3) {
                L_direct_C = 0;
                C_direct_R = 0;
            } direction_choice();
        }
        return true;
    };


    public void result_choice () {
        if (Int_left == 0 || Int_center == 0) { L_result_C = 0; }
        if (L_direct_C != 3 && p == 3 && L_result_C != 0){
            left_result_center.setText(String.valueOf(L_result_C));
            left_result_center.setVisibility(View.VISIBLE);
        } else {left_result_center.setVisibility(View.GONE);
            if (!menu_on && L_direct_C != 3 && p == 3){ L_direct_C = 0; } }

        if (Int_left == 0 || Int_right == 0) { L_result_R = 0; }
        if (L_direct_R != 3 && L_result_R != 0){
            left_result_right.setText(String.valueOf(L_result_R));
            left_result_right.setVisibility(View.VISIBLE);
        } else {left_result_right.setVisibility(View.GONE);
            if (!menu_on && L_direct_R != 3){ L_direct_R = 0; } }

        if (Int_center == 0 || Int_right == 0) { C_result_R = 0; }
        if (C_direct_R != 3 && p == 3 && C_result_R != 0){
            center_result_right.setText(String.valueOf(C_result_R));
            center_result_right.setVisibility(View.VISIBLE);
        } else {center_result_right.setVisibility(View.GONE);
            if (!menu_on && C_direct_R != 3 && p == 3) {C_direct_R = 0; } }
    }


    public void menu_visible() {
        if (!menu_on) {
            view_on = true; viwe_on_off();
            linear_modes_count_choice.setVisibility(View.GONE);
            linear_accident_player.setVisibility(View.GONE);
            button_clear.setText(R.string.stop);
            button_clear.getLayoutParams().height = (int)
                    (button_clear.getResources().getDisplayMetrics().density * 35);
        } else {
            view_on = false; viwe_on_off();
            linear_modes_count_choice.setVisibility(View.VISIBLE);
            linear_accident_player.setVisibility(View.VISIBLE);
            button_clear.setText(R.string.clear);
            button_clear.getLayoutParams().height = (int)
                    (button_clear.getResources().getDisplayMetrics().density * 50);
        }
    }


    public void viwe_on_off () {
        if (!view_on){
            button_clear.setEnabled(true); button_player.setEnabled(true);
            button_count.setEnabled(true); button_modes.setEnabled(true);

            editText_left.setEnabled(true); editText_center.setEnabled(true); editText_right.setEnabled(true);
            left_direct_center.setEnabled(true); left_direct_right.setEnabled(true); center_direct_right.setEnabled(true);
            editText_check();
        } else {
            if(menu_on){button_clear.setEnabled(false);} button_player.setEnabled(false);
            if (!c_on){button_count.setEnabled(false);} if (!m_on){button_modes.setEnabled(false);}
            if(menu_on){button_action.setEnabled(false);}

            editText_left.setEnabled(false); editText_center.setEnabled(false); editText_right.setEnabled(false);
            if (menu_on) {left_direct_center.setEnabled(false); left_direct_right.setEnabled(false); center_direct_right.setEnabled(false);}
        }
    }


    public void editText_check () {
        if (arguments == null) {
            string_left = editText_left.getText().toString().trim();
            string_center = editText_center.getText().toString().trim();
            string_right = editText_right.getText().toString().trim();
        }

        if (!string_left.isEmpty() && !string_center.isEmpty() && !string_right.isEmpty() && p == 3 && (!view_on || !menu_on)){
            button_action.setEnabled(true);
        } else if (!string_left.isEmpty() && !string_right.isEmpty() && p == 2 && (!view_on || !menu_on)) {
            button_action.setEnabled(true);
        } else {
           button_action.setEnabled(false);
           if (menu_on) {L_result_R = 0; L_result_C = 0; C_result_R = 0;}
        }
    }


    public void close_cursor (){
        if (!c_on && !m_on) {
            editText_left.setEnabled(false); editText_right.setEnabled(false); editText_center.setEnabled(false);
            editText_left.setEnabled(true); editText_center.setEnabled(true); editText_right.setEnabled(true);
        }

        if (c_on) {
            if (c == 3) {button_count.setText(R.string.notcount);}
            else if (c == 2) {button_count.setText(R.string.negative);}
            else if (c == 1) {button_count.setText(R.string.positive);}
            else {button_count.setText(R.string.count);}
            button_count.getLayoutParams().width = (int)
                    (button_count.getResources().getDisplayMetrics().density * 100);

            c_on = false;
            if (arguments == null) {
                linear_Right_num_change.setVisibility(View.GONE);
                linear_Center_num_change.setVisibility(View.GONE);
                linear_Left_num_change.setVisibility(View.GONE);
                textView_stiffcount.setVisibility(View.GONE);
                button_stiffLeft.setVisibility(View.GONE);
                button_stiffRight.setVisibility(View.GONE);
                button_notcount.setVisibility(View.GONE);
                linear_count_choice.setVisibility(View.GONE);
                button_modes.setVisibility(View.VISIBLE);
                view_on = false; viwe_on_off();
            }
        }

        if (m_on) {
            if (m == 4 || m == -4) {button_modes.setText(R.string.battleline);}
            else if (m == 3) {button_modes.setText(R.string.accident);}
            else if (m == 2) {button_modes.setText(R.string.direction);}
            else if (m == 1) {button_modes.setText(R.string.probably);}
            else {button_modes.setText(R.string.modes);}

            m_on = false;
            if (arguments == null) {
                button_direction.setVisibility(View.GONE);
                button_accident.setVisibility(View.GONE);
                button_battleline.setVisibility(View.GONE);
                if (m != 4) { view_on = false; viwe_on_off(); }
            } if (m == -4) {m = 4;}
        }
    }


    @Override
    public void onBackPressed() {
        if (system_back_time + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
        } system_back_time = System.currentTimeMillis();
    }


    public void saveData () {
        SharedPreferences.Editor editMainData = MainDataExchange.edit();
        editMainData.putString("languageCode", languageCode);

        editMainData.putString("string_left", string_left); editMainData.putString("string_center", string_center); editMainData.putString("string_right", string_right);
        editMainData.putInt("Int_left", Int_left); editMainData.putInt("Int_center", Int_center); editMainData.putInt("Int_right", Int_right);
        editMainData.putInt("L_result_C", L_result_C); editMainData.putInt("L_result_R", L_result_R); editMainData.putInt("C_result_R", C_result_R);
        editMainData.putInt("m", m); editMainData.putInt("c", c); editMainData.putInt("p", p);
        editMainData.putInt("L_direct_C", L_direct_C); editMainData.putInt("L_direct_R", L_direct_R); editMainData.putInt("C_direct_R", C_direct_R);
        editMainData.putInt("sc", (int) sc);

        editMainData.apply(); editMainData.commit();
    }

    public void outData () {
        languageCode = MainDataExchange.getString("languageCode", languageCode);

        string_left = MainDataExchange.getString("string_left", string_left) ; string_center = MainDataExchange.getString("string_center", string_center); string_right = MainDataExchange.getString("string_right", string_right) ;
        L_direct = MainDataExchange.getBoolean("L_direct", L_direct); R_direct = MainDataExchange.getBoolean("R_direct", R_direct);
        Int_left = MainDataExchange.getInt("Int_left", Int_left); Int_center = MainDataExchange.getInt("Int_center", Int_center); Int_right = MainDataExchange.getInt("Int_right", Int_right);
        L_result_C = MainDataExchange.getInt("L_result_C", L_result_C); L_result_R = MainDataExchange.getInt("L_result_R", L_result_R); C_result_R = MainDataExchange.getInt("C_result_R", C_result_R);
        m = (byte) MainDataExchange.getInt("m", m); c = (byte) MainDataExchange.getInt("c", c); p = (byte) MainDataExchange.getInt("p", p);
        L_direct_C = (byte) MainDataExchange.getInt("L_direct_C", L_direct_C); L_direct_R = (byte) MainDataExchange.getInt("L_direct_R", L_direct_R); C_direct_R = (byte) MainDataExchange.getInt("C_direct_R", C_direct_R);
        sc = MainDataExchange.getInt("sc", (int) sc);

        editText_left.setText(string_left); editText_center.setText(string_center); editText_right.setText(string_right);
        localization(languageCode);
        button_player.callOnClick(); c_on = true; m_on = true; close_cursor();
        result_choice(); direction_choice();
    }
}