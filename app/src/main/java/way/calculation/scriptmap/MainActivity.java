package way.calculation.scriptmap;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity { // Відповідає за вміст головного меню гри.

    // Це лише обявлені переміні цього класа, без привязки до ХМЛ.
    public Button button_clear, button_battleline, button_player, button_accident, button_stiffLeft,
            button_count, button_stiffRight, button_modes, button_notcount, button_positive, button_negative, button_action, button_direction;
    private LinearLayout linear_Left_Center_Right, linear_count_choice; // Це поверхність, на яку можна помістити контент, і управляти ним як окремою частиною.
    private EditText editText_left, editText_center, editText_right; // Це поля в які можна щось писати.
    public TextView textView_stiffcount, left_direct_center, left_direct_right, center_direct_right, // Це їх аналоги, але без змоги воду.
            left_result_center, left_result_right, center_result_right;
    public RelativeLayout launcher_background; // Це мабуть підключив заднік.
    private long system_clear_time, system_back_time; // Це невеличкі переміні які зберігають час кліка, в деяких функціях.

    public String string_left, string_center, string_right; // Це переміні які хранять текст, який я провіряю на пустоту, бо як конверсія з пустої строки в переміну визве помилку.
    public byte m = 0, c = 0, p = 2, L_direct_C = 0, L_direct_R = 0, C_direct_R = 0, b, bm; // Це скороченя від m = Mode, c = Count, p = Player, b = Basis, bm = BasisMode
    public double sc = 10; // Ця переміна має в собі ключове число яке впливає на результат сили атаки. =
    public boolean v_on = false, c_on = false, m_on = false, d_long_on = false; // Це переміні які я використовую як переключатілі на кнопках. v_on = Viwe_on, c_on = Count_on, m_on = Mode_on, d_long_on = Direct_long_on.

    @Override
    protected void onCreate(Bundle savedInstanceState) { // Цей клас, викликається на початку здійненя коду, тому в ньому виповниться все в першу чергу.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        linear_Left_Center_Right = findViewById(R.id.linear_Left_Center_Right);
        linear_count_choice = findViewById(R.id.linear_count_choice);

        editText_left = findViewById(R.id.Left_EditText);
        editText_center = findViewById(R.id.Center_EditText);
        editText_right = findViewById(R.id.Right_EditText);

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

        string_left = editText_left.getText().toString().trim(); // Це поміщеня в строку текста.
        string_center = editText_center.getText().toString().trim();
        string_right = editText_right.getText().toString().trim();

        launcher_background = findViewById(R.id.launcher_background);
        launcher_background.setOnLongClickListener(long_click_launcher_background);
        button_clear.setOnLongClickListener(long_click_clear);

        editText_left.addTextChangedListener(editText_watcher);
        editText_center.addTextChangedListener(editText_watcher);
        editText_right.addTextChangedListener(editText_watcher);

        // TODO Поки дані не обмінюються між активностями.
    } // І кстаті, цей код має послідовність, тобто функціонал вюшок розпосаний, від нижньої вшки до верхньої, йдучи зік заком, з ліва на право. І між методами, пропуски в два рядка.


    private TextWatcher editText_watcher = new TextWatcher() { // Це реєструє редагування ЕдітТекста користувачем.
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {editText_check();} // Викликає функцію яка провірить чи всі заповнені поля.
        @Override
        public void afterTextChanged(Editable s) {}
    };


    View.OnLongClickListener long_click_launcher_background = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) { // Це автоматичне виставлення в поля 50 очків
            button_clear.callOnClick();
            editText_right.setText(String.valueOf(50));
            if (p == 3){editText_center.setText(String.valueOf(50));}
            editText_left.setText(String.valueOf(50));
            return false;
        }
    };

    public void click_launcher_background (View view){ close_cursor(); } // В цей метод зруповані закриття меню, і забрання курсора.


    public void click_clear (View view){ // Очищує значення, і ставить їх в дефолтне положення.
        L_direct_R = 0;
        if (!d_long_on){ // Це відповідає щоб відключити стерання полей воду, як що гравець захоче очистити лише напрямки.
            editText_right.setText("");
            editText_left.setText("");
        } left_result_right.setVisibility(View.INVISIBLE); // Відключає відображення поля результату.
        if (p == 3 || system_clear_time + 300 > System.currentTimeMillis()) { // Очищення для 3 гравців. І тут реалізоване локальне очищення,
            // тобто скриті ЕдітПоля не стираються, тому код після "||" = "або", для того щоб, нажавши в промижутку часу два рази на кнопку очищення, стерти і не видемі.
            L_direct_C = 0;
            C_direct_R = 0;
            if (!d_long_on){ // Це відповідає щоб відключити стерання полей воду, як що гравець захоче очистити лише напрямки.
                editText_center.setText("");
            }
            left_result_center.setVisibility(View.INVISIBLE);
            center_result_right.setVisibility(View.INVISIBLE);
        }

        d_long_on = false;
        system_clear_time = System.currentTimeMillis(); // При першому клікови фіксується час нажаття. При повторному нажаті, провірка на час є вище, тому
        // тому провіриться старий записаний час, до якого додадутся секунди, що порівняються з теперішнім, і як що теперішній більший, то гравець нажав пізніше.
        direction_choice(); close_cursor(); // Це визови методів відображення напрямку, і закритя курсора.
    }

    View.OnLongClickListener long_click_clear = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) { // При довгому втримані на кнопку клір, скидуються кнопки на дефолт, як і режими, але не значення полей.
            m = 0; button_modes.setText(R.string.modes);
            c = 0; button_count.setText(R.string.count);
            sc = 10;
            return true; // "true" відповідає за те, що цей код не визве короткий клік, при натискані.
        }
    };


    public void click_player (View view){ // Це клік по додаваню гравців.
        p ++; // По дефолту значення 2, тому що початковий режим для двох гравців

        if (p == 3){ // Зміна на 3 гравців
            linear_Left_Center_Right.setVisibility(View.VISIBLE);
            editText_center.setVisibility(View.VISIBLE);
            button_player.setText(R.string.outline);
        }

        if (p == 4){ // Зміна на 4 гравців, який насправді перехід до 2
            p = 2;
            linear_Left_Center_Right.setVisibility(View.GONE);
            editText_center.setVisibility(View.INVISIBLE);
            button_player.setText(R.string.addline);
        }

        editText_check(); close_cursor(); // Це визови методів провірки на написаний текс в полях(просто кнопка повина бути виключена, як що лише двоє гравців
    } // заповнені, і повина бути включна, як що текс є і в третій, яка тепер не скрита), і закритя курсора.


    public void click_count (View view){ // Відповідає за жорсткосткість атак.
        switch (view.getId()){ // Не величка привязка до одного слухача нажаття, яка фіксує по айді, в одній системі.
            case R.id.button_stiffLeft: // Ліва кнопка. Більше число видасть менший результат. Зявляються після включення меню.
                sc += 2; if (sc > 20){ sc = 10; }
                break;
            case R.id.button_count: // Нажаття при закритому меню вибора, змінює цю кнопку до маленьких розмірів, і включає бокові кнопки.
                if (!c_on){ // Як що меню не відкрите.
                    c_on = true; v_on = true; viwe_on_off(); // Viwe_on_off відповідає за виключення постороніх кнопок, під час користування каунт меню.
                    button_modes.setVisibility(View.GONE);
                    button_count.getLayoutParams().width = (int)
                            (button_count.getResources().getDisplayMetrics().density * 28); // Зменшує кнопку, щоб помістити інші кнопки "вправо" і "вліво", не змінюючи дизайн в 100 діпі ширини кнопки.

                    button_count.setText(""); // Ми зменшили кнопку, тому її текс ми очищуємо. Він поставиться вже при закреті. І іншим методом.
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
                break;
            case R.id.button_stiffRight: // Права кнопка. Менше число видасть більший результат. Зявляються після включення меню.
                sc -= 2; if (sc < 0){ sc = 10; }
                break;
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

    public void click_count_choice (View view) { // Вибір методу рахування.
        switch (view.getId()){
            case R.id.button_notcount:
                c = 3;
                button_count.setText(R.string.notcount);
                break;
            case R.id.button_negative:
                c = 2;
                button_count.setText(R.string.negative);
                break;
            case R.id.button_positive:
                c = 1;
                button_count.setText(R.string.positive);
                break;
        }

        close_cursor (); // Щоб відключити відображення курсору, я прибіг по простому шляху, я віключаю і включаю ЕдітТексти, і це змінює відобрежання курсору на фолс.
    }


    public void click_modes (View view){ // Меню вибору режимів
        if (!m_on){
            m_on = true; v_on = true; viwe_on_off(); // viwe_on_off запускает метод, який відключає кнопки на час взамодії з меню.
            button_direction.setVisibility(View.VISIBLE);
            button_accident.setVisibility(View.VISIBLE);
            button_battleline.setVisibility(View.VISIBLE);
        } else {
            close_cursor (); // Також в цьому методі, зберігається закриття вікон, що реагує на значаня m_on = true .
        }

        if (m == 1 || m == 0){ // Дефолтний режем 0, але при відкреті я вітображаю меню 1.
            if (!m_on) { m = 1; } // Після відкритя меню m_on = true, новий клік по тійж кнопці викликає click_modes де стає v_on = false, і як що тут 0 аьо 1, змінюється на 1
            button_direction.setText(R.string.direction);
            button_modes.setText(R.string.probably);
            button_accident.setText(R.string.accident);
            button_battleline.setText(R.string.battleline);
        }

        if (m == 2){
            button_direction.setText(R.string.probably);
            button_modes.setText(R.string.direction);
            button_accident.setText(R.string.accident);
            button_battleline.setText(R.string.battleline);
        }

        if (m == 3){
            button_direction.setText(R.string.direction);
            button_modes.setText(R.string.accident);
            button_accident.setText(R.string.probably);
            button_battleline.setText(R.string.battleline);
        }

        // if (m == 4){}
    }


    public void click_mode_choice (View view){ // Вибір режиму
        switch (view.getId()){
            case R.id.button_direction:
                if (m != 2){m = 2;} else {m = 1;}
                break;
            case R.id.button_accident:
                if (m != 3){m = 3;} else {m = 1;}
                break;
            case R.id.button_battleline:
                if (m != 4){m = 4;
                    final Handler handler = new Handler(); // Цей метод створює затримку, для того щоб пройшла анімація, закривання меню.
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {  // TODO Цей код відповідає за передачу. Але ми його замінемо на внутрішнє збереження.
                            Intent intent = new Intent(MainActivity.this, MainBattleline.class); // Це створення обєкту який містить в собі запуск Активиті.
                            intent.putExtra("string_left", string_left);
                            intent.putExtra("string_center", string_center);
                            intent.putExtra("string_right", string_right);
                            intent.putExtra("sn", sc);
                            intent.putExtra("c", c);
                            intent.putExtra("p", p);
                            intent.putExtra("b", b);
                            intent.putExtra("bm", bm);
                            if (L_direct_C == 3) {intent.putExtra("L_direct_C", L_direct_C);}
                            if (L_direct_R == 3) {intent.putExtra("L_direct_R", L_direct_R);}
                            if (C_direct_R == 3) {intent.putExtra("C_direct_R", C_direct_R);}
                            startActivity(intent);
                            //finish(); // Можна завершити це активиті, а не тримати його в фоні.
                        }
                    }, 250);
                } else {m = 1;}
                break;
        }

        if (m == 1 && c == 3){button_count.setText(R.string.count); c = 0;} // Задумка в тому, що "не рахувати" дозволяє використати режим "випадковість", і прораховувати як монетку, без зачислення результатів.
        if (L_direct_C != 3){ L_direct_C = 0;}
        if (L_direct_R != 3){ L_direct_R = 0;}
        if (C_direct_R != 3){ C_direct_R = 0;}
        direction_choice();

        if (m == 4){
            close_cursor(); m = 0; button_modes.setText(R.string.battleline);
        } else {
            button_modes.callOnClick();
        }
    }


    public void click_action (View view){ // Цей метод викликається після кліку на Action, відповідає за надіслання даних в клас, і подальшуї оброботку.
        editText_left.setEnabled(false); editText_right.setEnabled(false); editText_center.setEnabled(false); // Відключає цифрові поля

        if (m == 0 || c == 0 || m == 2 && (L_direct_C + L_direct_R + C_direct_R) == 0){
            if (m == 0){button_modes.setEnabled(false);}
            if (c == 0){button_count.setEnabled(false);}
            if (m == 2 && (L_direct_C + L_direct_R + C_direct_R) == 0){
                left_direct_right.setVisibility(View.INVISIBLE);
                if (p == 3){
                    linear_Left_Center_Right.setVisibility(View.GONE);
                }
            }

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (m == 0){button_modes.setEnabled(true);}
                    if (c == 0){button_count.setEnabled(true);}
                    if (m == 2 && (L_direct_C + L_direct_R + C_direct_R) == 0){
                        left_direct_right.setVisibility(View.VISIBLE);
                        if (p == 3){
                            linear_Left_Center_Right.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }, 250);

        } else {
            // TODO Коряво зроблено/ Нужно переделать в метод сийд дата.
            int Int_left = 0, Int_center = 0, Int_right = 0;
            if (!string_left.isEmpty()) { Int_left = Integer.parseInt(string_left);}
            if (!string_center.isEmpty() && p != 2) { Int_center = Integer.parseInt(string_center); }
            if (!string_right.isEmpty()) { Int_right = Integer.parseInt(string_right); }

            if (Int_left + Int_right != 0 && p == 2 || Int_left + Int_center + Int_right != 0 && p == 3) { // TODO Здесь баг
                final ActionCalculator putCalculator = new ActionCalculator();

                if (p != 0){putCalculator.Int_left = Int_left;}
                if (p != 2){putCalculator.Int_center = Int_center;}
                if (p != 0){putCalculator.Int_right = Int_right;}

                if (p != 2) {
                    putCalculator.L_direct_C = L_direct_C;}
                if (p != 0) {
                    putCalculator.L_direct_R = L_direct_R;}
                if (p != 2) {
                    putCalculator.C_direct_R = C_direct_R;}


                if (Int_left + Int_center + Int_right != 0){
                    putCalculator.sc = sc; putCalculator.m = m; putCalculator.c = c;
                    putCalculator.Calculation();
                }


                int Final_left = 0, Final_center = 0, Final_right = 0;

                Final_left = putCalculator.Final_left;
                editText_left.setText(String.valueOf(Final_left));
                if (Int_center != 0 && p != 2){
                    Final_center = putCalculator.Final_center;
                    editText_center.setText(String.valueOf(Final_center));}
                Final_right = putCalculator.Final_right;
                editText_right.setText(String.valueOf(Final_right));


                if (Final_left == 0 && p == 2){
                    editText_left.setText("");
                } else if (Final_left == 0 && p == 3 && (Final_center == 0 || Final_right == 0)){
                    editText_left.setText(""); }
                if (Final_center == 0 && p == 3 && (Final_left == 0 || Final_right == 0)){
                    editText_center.setText("");}
                if (Final_right == 0 && p == 2){
                    editText_right.setText("");
                } else if (Final_right == 0 && p == 3 && (Final_left == 0 || Final_center == 0)){
                    editText_right.setText(""); }

                int L_result_C = putCalculator.L_result_C; int L_result_R = putCalculator.L_result_R; int C_result_R = putCalculator.C_result_R;
                left_result_center.setText(""); left_result_right.setText(""); center_result_right.setText("");

                if (L_direct_C != 3 && p == 3 && L_result_C != 0){
                    L_direct_C = putCalculator.L_direct_C;
                    left_result_center.setText(String.valueOf(L_result_C));
                    left_result_center.setVisibility(View.VISIBLE);
                } else {left_result_center.setVisibility(View.INVISIBLE); if (L_direct_C != 3 && p == 3){L_direct_C = 0;}}

                if (L_direct_R != 3 && L_result_R != 0){
                    L_direct_R = putCalculator.L_direct_R;
                    left_result_right.setText(String.valueOf(L_result_R));
                    left_result_right.setVisibility(View.VISIBLE);
                } else {left_result_right.setVisibility(View.INVISIBLE); if (L_direct_R != 3){L_direct_R = 0;}}

                if (C_direct_R != 3 && p == 3 && C_result_R != 0){
                    C_direct_R = putCalculator.C_direct_R;
                    center_result_right.setText(String.valueOf(C_result_R));
                    center_result_right.setVisibility(View.VISIBLE);
                } else {center_result_right.setVisibility(View.INVISIBLE); if (C_direct_R != 3 && p == 3){C_direct_R = 0;}}

                direction_choice();
            } else {
                if (Int_left == 0){editText_left.setText("");}
                if (Int_center == 0 && p == 3){editText_center.setText("");}
                if (Int_right == 0){ editText_right.setText("");}
            }
        }

        editText_left.setEnabled(true); editText_center.setEnabled(true); editText_right.setEnabled(true);
    }


    public void click_direction_choice (View view) {
        switch (view.getId()){
            case R.id.Left_direction_Center:
                if (m == 2){ L_direct_C++;}
                if (m == 2 && p == 2 && L_direct_C == 3){ L_direct_C = 1;}
                if (m == 2 && L_direct_C == 4){ L_direct_C = 1;}

                if (m != 2 && p == 3 && L_direct_C != 3){L_direct_C = 3;}
                else if (m != 2 && L_direct_C == 3){ L_direct_C = 0;}
                break;
            case R.id.Left_direction_Right:
                if (m == 2) {L_direct_R++;}
                if (m == 2 && p == 2 && L_direct_R == 3) {L_direct_R = 1;}
                if (m == 2 && L_direct_R == 4) {L_direct_R = 1;}

                if (m != 2 && p == 3 && L_direct_R != 3) {L_direct_R = 3;}
                else if (m != 2 && L_direct_R == 3) {L_direct_R = 0;}
                break;
            case R.id.Center_direction_Right:
                if (m == 2){ C_direct_R++;}
                if (m == 2 && p == 2 && C_direct_R == 3){ C_direct_R = 1;}
                if (m == 2 && C_direct_R == 4){ C_direct_R = 1;}

                if (m != 2 && p == 3 && C_direct_R != 3){C_direct_R = 3;}
                else if (m != 2 && C_direct_R == 3){ C_direct_R = 0;}
                break;
        }
        direction_choice();
    }

    public void direction_choice() {
        if (L_direct_C == 0){ left_direct_center.setText(R.string.Left_and_Center);}
        if (L_direct_C == 1){ left_direct_center.setText(R.string.Left_to_Center);}
        if (L_direct_C == 2){ left_direct_center.setText(R.string.Center_to_Left);}
        if (L_direct_C == 3 && p == 3){ left_direct_center.setText(R.string.Center_peace_Left);}

        if (L_direct_R == 0){ left_direct_right.setText(R.string.Left_and_Right);}
        if (L_direct_R == 2){ left_direct_right.setText(R.string.Left_to_Right);}
        if (L_direct_R == 1){ left_direct_right.setText(R.string.Right_to_Left);}
        if (L_direct_R == 3){ left_direct_right.setText(R.string.Left_peace_Right);}

        if (C_direct_R == 0){ center_direct_right.setText(R.string.Center_and_Right);}
        if (C_direct_R == 1){ center_direct_right.setText(R.string.Center_to_Right);}
        if (C_direct_R == 2){ center_direct_right.setText(R.string.Right_to_Center);}
        if (C_direct_R == 3 && p == 3){ center_direct_right.setText(R.string.Center_peace_Right);}
    }

    View.OnLongClickListener long_direction_choice = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if (m == 2){
                d_long_on = true;
                button_clear.callOnClick();
            }
            return true;
        }
    };


    public void viwe_on_off () {
        if (!v_on){
            button_clear.setEnabled(true); button_player.setEnabled(true);
            button_count.setEnabled(true); button_modes.setEnabled(true);

            editText_left.setEnabled(true); editText_center.setEnabled(true); editText_right.setEnabled(true);
            left_direct_center.setEnabled(true); left_direct_right.setEnabled(true); center_direct_right.setEnabled(true);
            editText_check();
        } else {
            button_clear.setEnabled(false); button_player.setEnabled(false);
            if (!c_on){button_count.setEnabled(false);} if (!m_on){button_modes.setEnabled(false);}
            button_action.setEnabled(false);

            editText_left.setEnabled(false); editText_center.setEnabled(false); editText_right.setEnabled(false);
            left_direct_center.setEnabled(false); left_direct_right.setEnabled(false); center_direct_right.setEnabled(false);
        }
    }


    public void editText_check () {
        string_left = editText_left.getText().toString().trim();
        string_center = editText_center.getText().toString().trim();
        string_right = editText_right.getText().toString().trim();

        if (!string_left.isEmpty() && !string_center.isEmpty() && !string_right.isEmpty() && p == 3){
            button_action.setEnabled(true);
        } else if (!string_left.isEmpty() && !string_right.isEmpty() && p == 2) {
            button_action.setEnabled(true);
        } else {
            button_action.setEnabled(false);
        }
    }


    public void close_cursor (){
        if (!c_on && !m_on) {
            editText_left.setEnabled(false); editText_right.setEnabled(false); editText_center.setEnabled(false);
            editText_left.setEnabled(true); editText_center.setEnabled(true); editText_right.setEnabled(true);
        }

        if (c_on) {
            if (c == 3){ button_count.setText(R.string.notcount); }
            else if (c == 2){ button_count.setText(R.string.negative); }
            else if (c == 1) { button_count.setText(R.string.positive); }
            else { button_count.setText(R.string.count); }
            button_count.getLayoutParams().width = (int)
                    (button_count.getResources().getDisplayMetrics().density * 100);

            textView_stiffcount.setVisibility(View.GONE);
            button_stiffLeft.setVisibility(View.GONE);
            button_stiffRight.setVisibility(View.GONE);
            button_notcount.setVisibility(View.GONE);
            linear_count_choice.setVisibility(View.GONE);
            button_modes.setVisibility(View.VISIBLE);
            c_on = false; v_on = false; viwe_on_off();
        }

        if (m_on) {
            if (m == 0){button_modes.setText(R.string.modes);}
            button_direction.setVisibility(View.GONE);
            button_accident.setVisibility(View.GONE);
            button_battleline.setVisibility(View.GONE);
            m_on = false; v_on = false; viwe_on_off();
        }
    }


    @Override
    public void onBackPressed() {
        if (system_back_time + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
        } system_back_time = System.currentTimeMillis();
    }
}