package way.calculation.scriptmap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ToggleButton;

import androidx.core.content.ContextCompat;

import java.util.Locale;


public class MainBattleline extends MainData {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_battleline_land);
        MainDataExchange = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        button_clear = findViewById(R.id.button_clear);
        button_continue = findViewById(R.id.button_restore);
        button_dynamicall = findViewById(R.id.button_dynamicall);
        button_mechanical = findViewById(R.id.button_mechanical);
        button_positive = findViewById(R.id.button_positive);
        button_notcount = findViewById(R.id.button_notcount);
        button_negative = findViewById(R.id.button_negative);
        button_stiffLeft = findViewById(R.id.button_stiffLeft);
        button_count = findViewById(R.id.button_count);
        button_creative = findViewById(R.id.button_creative);
        button_stiffRight = findViewById(R.id.button_stiffRight);
        button_basisofmodeLeft = findViewById(R.id.button_basisofmodeLeft);
        button_basis = findViewById(R.id.button_basis);
        button_basisofmodeRight = findViewById(R.id.button_basisofmodeRight);
        button_action = findViewById(R.id.button_action);
        button_player = findViewById(R.id.button_player);
        button_modes = findViewById(R.id.button_modes);

        button_attack_Left = findViewById(R.id.button_attack_Left);
        button_attack_Right = findViewById(R.id.button_attack_Right);
        button_move_Left = findViewById(R.id.button_move_Left);
        button_move_Right = findViewById(R.id.button_move_Right);

        button_up_flank_Left = findViewById(R.id.button_up_flank_Left);
        button_center_flank_Left = findViewById(R.id.button_center_flank_Left);
        button_down_flank_Left = findViewById(R.id.button_down_flank_Left);
        button_up_flank_Right = findViewById(R.id.button_up_flank_Right);
        button_center_flank_Right = findViewById(R.id.button_center_flank_Right);
        button_down_flank_Right = findViewById(R.id.button_down_flank_Right);

        button_defense_Left = findViewById(R.id.button_defense_Left);
        button_defense_Right = findViewById(R.id.button_defense_Right);

        button_attack_Left.setOnTouchListener(click_attack_Left);
        button_attack_Right.setOnTouchListener(click_attack_Right);
        button_move_Left.setOnTouchListener(click_move_Left);
        button_move_Right.setOnTouchListener(click_move_Right);
        button_defense_Left.setOnTouchListener(click_defense_Left);
        button_defense_Right.setOnTouchListener(click_defense_Right);

        button_move_Left.setOnLongClickListener(long_click_move_Left);
        button_move_Right.setOnLongClickListener(long_click_move_Right);

        linear_Left_Right_num_change = findViewById(R.id.linear_Left_Right_num_change);
        linear_Right_num_change = findViewById(R.id.linear_Right_num_change);
        linear_Left_num_change = findViewById(R.id.linear_Left_num_change);
        linear_count_choice = findViewById(R.id.linear_count_choice);
        linear_basisofmode_choice = findViewById(R.id.linear_basisofmode_choice);
        linear_basis_choice = findViewById(R.id.linear_basis_choice);
        linear_menu = findViewById(R.id.linear_menu);
        linear_flank_Left = findViewById(R.id.linear_flank_Left);
        linear_flank_Right = findViewById(R.id.linear_flank_Right);

        frameLayout_seekBar = findViewById(R.id.frameLayout_seekBar);

        left_direct_right = findViewById(R.id.Left_direction_Right);
        left_direct_right.setOnLongClickListener(long_direction_choice);
        textView_basisofmode = findViewById(R.id.textView_basisofmode);
        textView_stiffcount = findViewById(R.id.textView_stiffcount);
        left_result_right = findViewById(R.id.Left_result_Right);

        left_seekBar_right = findViewById(R.id.Left_seekBar_Right);
        right_seekBar_left = findViewById(R.id.Right_seekBar_Left);
        left_seekBar_right.setEnabled(false);
        right_seekBar_left.setEnabled(false);

        launcher_background = findViewById(R.id.launcher_background);
        launcher_background.setOnLongClickListener(long_click_launcher_background);
        button_clear.setOnLongClickListener(long_click_clear);

        editText_left_right = findViewById(R.id.Left_EditText_Right);
        editText_left_down = findViewById(R.id.Left_Down_EditText);
        editText_right_up = findViewById(R.id.Right_Up_EditText);
        editText_left_right.addTextChangedListener(editText_watcher);
        editText_left_down.addTextChangedListener(editText_watcher);
        editText_right_up.addTextChangedListener(editText_watcher);

        editText_left = findViewById(R.id.Left_EditText);
        editText_right = findViewById(R.id.Right_EditText);
        editText_left.addTextChangedListener(editText_watcher);
        editText_right.addTextChangedListener(editText_watcher);

        if (StartData) { setRotationAnimation(); outData(); }
    }


    private void setRotationAnimation() {
        int rotationAnimation = WindowManager.LayoutParams.ROTATION_ANIMATION_JUMPCUT;
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        winParams.rotationAnimation = rotationAnimation;
        win.setAttributes(winParams);
    }

    public void localization (String languageCode) { // TODO Локалізація погано працює
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(config, null);

        button_clear.setText(R.string.clear);
        button_continue.setText(R.string.continue_action);
        button_dynamicall.setText(R.string.dynamicall);
        button_mechanical.setText(R.string.mechanical);
        button_positive.setText(R.string.positive);
        button_notcount.setText(R.string.notcount);
        button_negative.setText(R.string.negative);
        if (k == 1) {button_action.setText(R.string.creative);} else {button_action.setText(R.string.action);}
        if (p == 3) {button_player.setText(R.string.outline);} else {button_player.setText(R.string.addline);}
        if (m == 4) {button_modes.setText(R.string.battleline);}
        button_attack_Left.setText(R.string.attack);
        button_attack_Right.setText(R.string.attack);
        button_move_Left.setText(R.string.move);
        button_move_Right.setText(R.string.move);
        button_defense_Left.setText(R.string.defense);
        button_defense_Right.setText(R.string.defense);
    }


    private final TextWatcher editText_watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {editText_check();}
        @Override
        public void afterTextChanged(Editable s) {}
    };


    View.OnLongClickListener long_click_launcher_background = v -> {
        if (menu_on && !m_on){
            button_clear.callOnClick();
            editText_right.setText(String.valueOf(50));
            editText_left.setText(String.valueOf(50));
        } else if (m_on) { // Пасхалка...
            launcher_background.setBackgroundResource(R.color.colorOld_ScriptMap);
            //launcher_background.setBackgroundColor(Color.parseColor("#232933"));
        }
        return false;
    };

    public void click_launcher_background (View view){ close_cursor();}


    public void click_clear (View view){ // TODO Очищує показники.
        if (menu_on){
            L_seekbarLenght_R = 0; L_seekbar_R = 0; R_seekbar_L = 0;
            L_position_R = 0; R_position_L = 0;
            L_direct = true; R_direct = true;

            L_direct_R = 0;  L_result_R = 0;
            editText_right.setText(""); editText_left.setText("");

            restore = 0; if (k == 1) { clear_creative(); }
            result_choice(); direction_choice(); seekbar_choice(); close_cursor();
        }
    }


    View.OnLongClickListener long_click_clear = v -> {
        if (!menu_on){ // TODO Зупинняє битву.
            button_move_Left.setEnabled(false); button_move_Right.setEnabled(false);
            menu_on = true; menu_visible(); result_choice();
            button_continue.setVisibility(View.GONE);
            if (k == 1 && !move_on) {creative();}
            if (move_on) {if (restore != 2) {restore = 1;} check_start();}
            if (string_left.equals("0")){editText_left.setText("");}
            if (string_right.equals("0")){ editText_right.setText("");}
        } else {
            if (languageCode.equals("default")) {
                languageCode  = "uk";
            } else {languageCode = "default";}

            localization(languageCode);
            b = -1; button_basis.setText(R.string.basis);
            c = 0; button_count.setText(R.string.count);
            k = 0; button_action.setText(R.string.action);
            button_continue.setText(R.string.continue_action);
            sc = 10; bm = 2; anim_base_choice();
            view_on = false; viwe_on_off(); close_cursor();

            SharedPreferences.Editor editMainData = MainDataExchange.edit();
            editMainData.clear(); editMainData.apply();
        }
        return true;
    };


    public void click_restore (View view) { // TODO Відповідає за відновлння минулих показників.
        if (!menu_on){
            boolean button_restore = ((ToggleButton) view).isChecked();
            if (k == 1) {
                button_continue.setChecked(false);
                button_continue.setText(R.string.clear);
                clear_creative();
            } else {
                if (button_restore){
                    restore = 2; // Продовжити
                } else {
                    restore = 1; // Не продовжувати
                } seekbar_choice();
            }
        } else {
            if (k == 1) {
                button_continue.setVisibility(View.VISIBLE);
                button_continue.setChecked(false);
                button_continue.setText(R.string.clear);
            } else {
                if (restore == 0) {
                    button_continue.setChecked(false);
                    button_continue.setVisibility(View.GONE);
                } else if (restore == 1) {
                    button_continue.setVisibility(View.VISIBLE);
                    button_continue.setChecked(false);
                } else if (restore == 2) {
                    button_continue.setVisibility(View.VISIBLE);
                    button_continue.setChecked(true);
                }
            } button_continue.setTextSize(8);
        }
    }


    public void click_count (View view){
        int id = view.getId();
        if (id == R.id.button_stiffLeft) {
            sc += 2; if (sc > 20){ sc = 10; }
        } else if (id == R.id.button_count) {
            if (!c_on){
                c_on = true; view_on = true; viwe_on_off();
                button_clear.setVisibility(View.GONE);
                button_count.getLayoutParams().width = (int)
                        (button_count.getResources().getDisplayMetrics().density * 28);

                button_count.setText("");
                linear_Right_num_change.setVisibility(View.VISIBLE);
                linear_Left_num_change.setVisibility(View.VISIBLE);
                button_notcount.setVisibility(View.VISIBLE);
                textView_stiffcount.setVisibility(View.VISIBLE);
                linear_count_choice.setVisibility(View.VISIBLE);
                button_stiffLeft.setVisibility(View.VISIBLE);
                button_stiffRight.setVisibility(View.VISIBLE);
                button_notcount.setTextSize(8);
            } else {sc = 10; textView_stiffcount.setText(Html.fromHtml(getString(R.string.stiffprimary)));}
        } else if (id == R.id.button_stiffRight) {
            sc -= 2; if (sc < 0){ sc = 10; }
        }

        if (c_on) {
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

    public void click_count_choice (View view) {
        int id = view.getId();
        if (id == R.id.button_notcount) {
            c = 3; button_count.setText(R.string.notcount);
        } else if (id == R.id.button_negative) {
            c = 2; button_count.setText(R.string.negative);
        } else if (id == R.id.button_positive) {
            c = 1; button_count.setText(R.string.positive);
        }
        close_cursor ();
    }

    public void click_count_number (View view) {
        if (c_on) { int id = view.getId();

            if (id == R.id.Left_num_plus || id == R.id.Left_num_minus) {if (!string_left.isEmpty()) { Int_left = Integer.parseInt(string_left); } else {Int_left = 0;}}
            if (id == R.id.Left_num_plus)    { if (Int_left < 999999) {Int_left++; editText_left.setText(String.valueOf(Int_left));}
            } else if (id == R.id.Left_num_minus)   { if (Int_left > 1) {Int_left--; editText_left.setText(String.valueOf(Int_left));} else {editText_left.setText("");} }

            if (id == R.id.Right_num_plus || id == R.id.Right_num_minus) {if (!string_right.isEmpty()) { Int_right = Integer.parseInt(string_right); } else {Int_right = 0;}}
            if (id == R.id.Right_num_plus)   { if (Int_right < 999999) {Int_right++; editText_right.setText(String.valueOf(Int_right));}
            } else if (id == R.id.Right_num_minus)  { if (Int_right > 1) {Int_right--; editText_right.setText(String.valueOf(Int_right));} else {editText_right.setText("");} }
        }

        if (k == 1 && !menu_on && !move_on) { int id = view.getId();
            if (id == R.id.Left_Right_num_plus || id == R.id.Left_Right_num_minus) {if (!string_left_right.isEmpty()) { Int_left_right = Integer.parseInt(string_left_right); } else {L_seekbarLenght_R = 0;}}
            if (id == R.id.Left_Right_num_plus)   { if (Int_left_right < 999999) {Int_left_right++; editText_left_right.setText(String.valueOf(Int_left_right));}
            } else if (id == R.id.Left_Right_num_minus)  { if (Int_left_right > 1) {Int_left_right--; editText_left_right.setText(String.valueOf(Int_left_right));} else {editText_left_right.setText("");} }
        }
    }

    public void creative () {
        final Handler handler = new Handler(); // TODO Дописати

        if (!menu_on && !move_on) { button_clear.setEnabled(false);
            handler.postDelayed(() -> {
                editText_left_right.setVisibility(View.GONE);
                if (b == 1) { editText_left_down.setVisibility(View.GONE); editText_right_up.setVisibility(View.GONE);}
                editText_left.setVisibility(View.GONE); editText_right.setVisibility(View.GONE);

                editText_left_right.getLayoutParams().width = (int)
                        (editText_left_right.getResources().getDisplayMetrics().density * 490);

            }, 200);
            handler.postDelayed(() -> {
                editText_left_right.setVisibility(View.VISIBLE); editText_left_right.setEnabled(true);
                if (b == 1) { editText_left_down.setVisibility(View.VISIBLE); editText_right_up.setVisibility(View.VISIBLE);
                    editText_left_down.setEnabled(true); editText_right_up.setEnabled(true); }
                editText_left.setVisibility(View.VISIBLE); editText_right.setVisibility(View.VISIBLE);
                editText_left.setEnabled(true); editText_right.setEnabled(true);

                if (Int_left_k != 0) {editText_left.setText(String.valueOf(Int_left_k));}
                if (Int_right_k != 0) {editText_right.setText(String.valueOf(Int_right_k));}
                if (Int_left_down != 0) {editText_left_down.setText(String.valueOf(Int_left_down));}
                if (Int_right_up != 0) {editText_right_up.setText(String.valueOf(Int_right_up));}
                if (Int_left_right != 0) {editText_left_right.setText(String.valueOf(Int_left_right));}

                button_clear.setEnabled(true);
            }, 500);
        } else if (!move_on) {

            editText_left_right.setVisibility(View.GONE); editText_left_right.setEnabled(false);
            if (b == 1) { editText_left_down.setEnabled(false); editText_right_up.setEnabled(false);}
            editText_left.setEnabled(true); editText_right.setEnabled(true);

            editText_left_right.getLayoutParams().width = (int)
                    (editText_left_right.getResources().getDisplayMetrics().density * 80);
            editText_left_right.setVisibility(View.VISIBLE);

            if (!string_left.isEmpty()) {Int_left_k = Integer.parseInt(string_left);}else{Int_left_k = 0;}
            if (!string_right.isEmpty()) {Int_right_k = Integer.parseInt(string_right);}else{Int_right_k = 0;}
            if (!string_left_down.isEmpty()) {Int_left_down = Integer.parseInt(string_left_down);}else{Int_left_down = 0;}
            if (!string_right_up.isEmpty()) {Int_right_up = Integer.parseInt(string_right_up);}else{Int_right_up = 0;}
            if (!string_left_right.isEmpty()) {Int_left_right = Integer.parseInt(string_left_right);}else{Int_left_right = 0;}

            battleline_count();
        }
    }

    public void clear_creative () {
        if (!menu_on) { editText_left.setText(""); editText_right.setText(""); }
        editText_left_down.setText(""); editText_right_up.setText("");
        editText_left_right.setText(""); Int_left_k = 0; Int_right_k = 0;
        Int_left_right = 0; Int_left_down = 0; Int_right_up = 0;
    }


    public void click_basis (View view){ // TODO Відповідає за вплив врахування очків на гемпей
        int id = view.getId();
        if (id == R.id.button_basisofmodeLeft) {
            if (bm > 2){ bm = 1;
            } else { bm --; if (bm < 1){ bm = 2; } }
        } else if (id == R.id.button_basis) {
            if (!b_on){
                b_on = true; view_on = true; viwe_on_off();
                textView_basisofmode.setVisibility(View.VISIBLE);
                linear_basis_choice.setVisibility(View.VISIBLE);
                linear_basisofmode_choice.setVisibility(View.VISIBLE);
                button_creative.setVisibility(View.VISIBLE);

                button_basis.setVisibility(View.GONE);
                button_clear.setVisibility(View.GONE);
                button_count.getLayoutParams().height = (int)
                        (button_count.getResources().getDisplayMetrics().density * 31);
                button_count.setTextSize(10);
                button_creative.setTextSize(8);
            }
        } else if (id == R.id.button_basisofmodeRight) {
            if (bm < 2) { bm = 3;
            } else { bm ++; if (bm > 3){ bm = 2; } }
        } else if (id == R.id.button_creative) {
            k ++; if (k >= 2) {k = 0;}
        }

        if (b_on) {
            if (bm == 3) { // Відсутній.
                String string_isabsent = getString(R.string.isabsent);
                textView_basisofmode.setText(Html.fromHtml(getString(R.string.basisofmode, string_isabsent)));
            } else if (bm == 2) { // Залежний.
                String string_dependent = getString(R.string.dependent);
                textView_basisofmode.setText(Html.fromHtml(getString(R.string.basisofmode, string_dependent)));
            } else if (bm == 1) { // Одноманітний.
                String string_monotonous = getString(R.string.monotonous);
                textView_basisofmode.setText(Html.fromHtml(getString(R.string.basisofmode, string_monotonous)));}
            textView_basisofmode.setTextSize(12);

            if (k == 1){ // Креатив.
                button_action.setText(R.string.creative); button_creative.setText(R.string.action);
                if (b != 0) { editText_left_right.setVisibility(View.VISIBLE); linear_Left_Right_num_change.setVisibility(View.VISIBLE);}
                if (b == 1) { editText_left_down.setVisibility(View.VISIBLE); editText_right_up.setVisibility(View.VISIBLE); }
            } else if (k == 0){
                button_action.setText(R.string.action); button_creative.setText(R.string.creative);
                editText_left_down.setVisibility(View.GONE); editText_right_up.setVisibility(View.GONE);
                editText_left_right.setVisibility(View.GONE); linear_Left_Right_num_change.setVisibility(View.GONE);
            }
        }
    }

    public void click_base_choice (View view){
        int id = view.getId();
        if (id == R.id.button_mechanical) {
            if (b != 2) {b = 2;} else {id = 0;}
        } else if (id == R.id.button_dynamicall) {
            if (b != 1) {b = 1;} else {id = 0;}
        }

        close_cursor ();
        direction_choice();
        if (id != 0) {anim_base_choice();}
    }

    public void anim_base_choice() { // TODO Відповідає за анімацію кнопок, руху, фронта, атаки, захиста.
        final Handler handler = new Handler();
        if (b != 0) {
            if (b == -1) {b = 0;}
            button_move_Left.setVisibility(View.GONE); button_move_Right.setVisibility(View.GONE);
            linear_flank_Left.setVisibility(View.GONE); linear_flank_Right.setVisibility(View.GONE);
            editText_left_down.setVisibility(View.GONE); editText_right_up.setVisibility(View.GONE);
            editText_left_right.setVisibility(View.GONE);
            handler.postDelayed(() -> {
                button_attack_Left.setVisibility(View.GONE); button_attack_Right.setVisibility(View.GONE);
                button_defense_Left.setVisibility(View.GONE); button_defense_Right.setVisibility(View.GONE);
                linear_Left_Right_num_change.setVisibility(View.GONE);}, 200);
        }

        if (b == 2){ // mechanical TODO доробити анімацію
            view_on = true; viwe_on_off();
            handler.postDelayed(() -> {
                if (k == 1){ linear_Left_Right_num_change.setVisibility(View.VISIBLE); }
                editText_left.setVisibility(View.GONE); editText_right.setVisibility(View.GONE);
            }, 500);
            handler.postDelayed(() -> {
                button_attack_Left.setVisibility(View.VISIBLE); button_attack_Right.setVisibility(View.VISIBLE);
                button_defense_Left.setVisibility(View.VISIBLE); button_defense_Right.setVisibility(View.VISIBLE);
            }, 700); // 700
            handler.postDelayed(() -> {
                editText_left.setVisibility(View.VISIBLE); editText_right.setVisibility(View.VISIBLE);
            }, 900); // 900
            handler.postDelayed(() -> {
                if (k == 1){ editText_left_right.setVisibility(View.VISIBLE); }
                button_move_Left.setVisibility(View.VISIBLE); button_move_Right.setVisibility(View.VISIBLE);
                linear_flank_Left.setVisibility(View.VISIBLE); linear_flank_Right.setVisibility(View.VISIBLE);
                if (k == 0) { view_on = false; viwe_on_off();}
            }, 1050); // 1050
            if (k == 1) {handler.postDelayed(() -> {
                view_on = false; viwe_on_off();
            }, 1200); }
        }

        if (b == 1) { // dynamicall TODO доробити анімацію
            view_on = true; viwe_on_off();
            handler.postDelayed(() -> {
                if (k == 1){ linear_Left_Right_num_change.setVisibility(View.VISIBLE); }
                editText_left.setVisibility(View.GONE); editText_right.setVisibility(View.GONE);
            }, 500);
            handler.postDelayed(() -> {
                button_attack_Left.setVisibility(View.VISIBLE); button_attack_Right.setVisibility(View.VISIBLE);
                button_defense_Left.setVisibility(View.VISIBLE); button_defense_Right.setVisibility(View.VISIBLE);
            }, 700);
            handler.postDelayed(() -> {
                if (k == 1){
                    editText_left_down.setVisibility(View.VISIBLE); editText_right_up.setVisibility(View.VISIBLE);}
                editText_left.setVisibility(View.VISIBLE); editText_right.setVisibility(View.VISIBLE);
            }, 900);
            handler.postDelayed(() -> {
                if (k == 1){ editText_left_right.setVisibility(View.VISIBLE); }
                button_attack_Left.setVisibility(View.GONE); button_attack_Right.setVisibility(View.GONE);
                button_defense_Left.setVisibility(View.GONE); button_defense_Right.setVisibility(View.GONE);
                button_move_Left.setVisibility(View.VISIBLE); button_move_Right.setVisibility(View.VISIBLE);
                linear_flank_Left.setVisibility(View.VISIBLE); linear_flank_Right.setVisibility(View.VISIBLE);
                if (k == 0){view_on = false; viwe_on_off();}
            }, 1050);
            if (k == 1) {handler.postDelayed(() -> {
                view_on = false; viwe_on_off();
            }, 1200); }
        }
    }


    public void click_action (View view){ // TODO Відповідає за готовність до гри - обрахунку
        if (b == 0 || c == 0){ // Провірка чи виконані умови для продовження
            if (b == 0){button_basis.setEnabled(false);}
            if (c == 0){button_count.setEnabled(false);}

            final Handler handler = new Handler();
            handler.postDelayed(() -> {
                if (b == 0 && !c_on){button_basis.setEnabled(true);}
                if (c == 0 && !b_on){button_count.setEnabled(true);}
            }, 250);

        } else {

            editText_Integer();

            if (Int_left != 0 && Int_right != 0){ // Провірка чи не нульове значення в полях. + Перехід в меню боя.

                button_continue.callOnClick();
                menu_on = false; menu_visible(); // Кладеться значення в "меню виключенне? - так", і викликається його закриття
                if (k == 1) { creative();
                } else { button_move_Left.setEnabled(true); button_move_Right.setEnabled(true); }
            } else {
                close_cursor();
                if (string_left.equals("0")){editText_left.setText("");}
                if (string_right.equals("0")){ editText_right.setText("");}
            }
        }
    }


    View.OnTouchListener click_attack_Left = (view, event) -> {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        view.performClick();
        return false;
    };


    View.OnTouchListener click_attack_Right = (view, event) -> {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        view.performClick();
        return false;
    };


    View.OnTouchListener click_move_Left = (view, event) -> {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (move_on) {
                    if (b == 2){ // mechanical
                    }

                    if (b == 1){ // dynamicall
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (!move_on) {
                    button_move_Left.setEnabled(false);
                    system_start_Left_time = System.currentTimeMillis();
                    check_start();

                    Handler handler = new Handler();
                    handler.postDelayed(() -> {
                        if (!move_on && !menu_on){
                            button_move_Left.setEnabled(true);
                        }
                    }, 1200);
                } else {

                }
                break;
        }
        view.performClick();
        return false;
    };

    View.OnLongClickListener long_click_move_Left = v -> {
        if (move_on){}
        return true;
    };

    View.OnTouchListener click_move_Right = (view, event) -> {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (move_on) {
                    if (b == 2){ // mechanical
                        if (R_seekbar_L != 0){
                            R_seekbar_L -= 1;
                            editText_right.setText(String.valueOf(R_seekbar_L));
                            //frameLayout_seekBar.bringChildToFront(findViewById(R.id.Right_seekBar_Left));
                            //right_seekBar_left.bringToFront();

                            if (R_direct) {
                                if (R_position_L >= L_seekbarLenght_R){
                                    R_direct = false; R_position_L--;
                                } else {
                                    R_position_L++;
                                }
                            } else {
                                if (R_position_L <= 0){
                                    R_direct = true; R_position_L++;
                                } else {
                                    R_position_L--;
                                }
                            }
                            System.out.println(R_position_L);
                            right_seekBar_left.setProgress(R_position_L);
                        } else {button_move_Right.setEnabled(false);}
                    }

                    if (b == 1){ // dynamicall
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (!move_on) {
                    button_move_Right.setEnabled(false);
                    system_start_Right_time = System.currentTimeMillis();
                    check_start();

                    Handler handler = new Handler();
                    handler.postDelayed(() -> {
                        if (!move_on && !menu_on){
                            button_move_Right.setEnabled(true);
                        }
                    }, 1200);
                } else {

                }
                break;
        }
        view.performClick();
        return false;
    };

    View.OnLongClickListener long_click_move_Right = v -> {
        if (move_on){}
        return true;
    };


    public void check_start() { // TODO Зрівнює таймінги відпускання суперниками move кнопки
        if (!move_on) {
            if (system_start_Left_time + 120 > System.currentTimeMillis() && system_start_Right_time + 120 > System.currentTimeMillis()){ // На основі фіксації часу проводиться зрівняння.
                move_on = true; battleline_count();
                //restore = 0; button_restore.callOnClick();
                button_continue.setVisibility(View.GONE);
                left_result_right.setVisibility(View.VISIBLE);

                left_direct_right.setRotation(0);
                left_direct_right.getLayoutParams().width = (int)
                        (left_direct_right.getResources().getDisplayMetrics().density * 490); // 490
                left_direct_right.setProgressDrawable(ContextCompat.getDrawable(this, R.drawable.direct_battleline));

                Handler handler = new Handler();
                handler.postDelayed(() -> {
                    if (move_on) {
                        button_move_Left.setEnabled(true);
                        button_move_Right.setEnabled(true);
                        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        vibrator.vibrate(VibrationEffect.createOneShot(15, VibrationEffect.DEFAULT_AMPLITUDE));
                    }
                }, 400);
                    handler.postDelayed(() -> {
                        if (move_on) {
                            if (b == 2) { // mechanical
                                button_attack_Left.setEnabled(true);
                                button_attack_Right.setEnabled(true);
                                button_defense_Left.setEnabled(true);
                                button_defense_Right.setEnabled(true);
                            } // else if (b == 1) {} // dynamicall
                            button_up_flank_Left.setEnabled(true);
                            button_center_flank_Left.setEnabled(true);
                            button_down_flank_Left.setEnabled(true);
                            button_up_flank_Right.setEnabled(true);
                            button_center_flank_Right.setEnabled(true);
                            button_down_flank_Right.setEnabled(true);
                        }
                    }, 1000);
            } // else {}
        } else {
            move_on = false; battleline_count();

            button_attack_Left.setEnabled(false);
            button_attack_Right.setEnabled(false);
            button_up_flank_Left.setEnabled(false);
            button_center_flank_Left.setEnabled(false);
            button_down_flank_Left.setEnabled(false);
            button_up_flank_Right.setEnabled(false);
            button_center_flank_Right.setEnabled(false);
            button_down_flank_Right.setEnabled(false);
            button_defense_Left.setEnabled(false);
            button_defense_Right.setEnabled(false);
            left_direct_right.getLayoutParams().width = (int)
                    (left_direct_right.getResources().getDisplayMetrics().density * 510);
            direction_choice();
        }
    }


    public void battleline_count() { //TODO Розрахунок значень в полях.
        // TODO Використати нижнє в верхнє поле для сум захвата і іншого.
        if (move_on) {
            // або як що вписати то швидкість буде виходити з цього показника.
               // Як що основа режиму 3 "довічне" то, очки не будуть обчислюватись, але як що гравець не виставить очки, задається стандартна швидкість,

            if (k == 1) {}

        if (bm == 3) { // Вплив очок на гемплей відсутній.

            } else if (bm == 2) { // Вплив очок на гемплей звичайний.

            } else if (bm == 1) { // Вплив очок на гемплей одноманітний.
                }

            if (restore == 2) {
                L_percen_R = L_seekbar_R * 1.0 / OLD_L_seekbar_R * 100;
                R_percen_L = R_seekbar_L * 1.0 / OLD_R_seekbar_L * 100;
                L_percenAnd_R = L_sekbarResult_R * 1.0 / OLD_L_Int_and_R * 100;
                L_percenPost_R = L_position_R * 1.0 / L_seekbarLenght_R * 100;
                R_percenPost_L = R_position_L * 1.0 / L_seekbarLenght_R * 100;
            }

            L_sekbarResult_R = Int_left + Int_right; // Спільне значення
            L_seekbarLenght_R = L_sekbarResult_R; // Довжина шляху
            double L_double_R = L_sekbarResult_R; int degree_LR, result_degree;

            if (L_seekbarLenght_R > 25) { // Система розрахунку оптимальної суми кліків для пройдення поля
                for (degree_LR = 2, result_degree = 1 + L_seekbarLenght_R; result_degree > L_seekbarLenght_R; degree_LR ++) {
                    result_degree = (L_seekbarLenght_R / degree_LR * (L_seekbarLenght_R / degree_LR));
                } L_seekbarLenght_R = degree_LR + 19;
            }

            field_separation(L_double_R);

            if (b == 2){ // mechanical // Підрахунок індевідуальних сум
                L_seekbar_R = roundedL + 2 + (L_seekbarLenght_R - (L_seekbarLenght_R / roundedL));  // Кількість кліків лівого
                R_seekbar_L = roundedR + 2 + (L_seekbarLenght_R - (L_seekbarLenght_R / roundedR)); // Кількість кліків правого
                L_sekbarResult_R = (L_seekbar_R + R_seekbar_L + L_seekbarLenght_R) / 2; // Число спільної суми кліків
            }

            if (b == 1){ // dynamicall
                L_speed_R = roundedL; R_speed_L = roundedR;

                for (degree_LR = 0, result_degree = 0; result_degree < 200; degree_LR ++){
                    result_degree = L_seekbarLenght_R * degree_LR;
                    L_sekbarResult_R = result_degree - degree_LR;
                } L_seekbarLenght_R += L_sekbarResult_R;
                field_separation(L_double_R);
                L_seekbar_R = (int) Math.round(((20.0 / roundedL) + 1) * ((L_seekbarLenght_R - L_sekbarResult_R) * (L_seekbarLenght_R)));
                R_seekbar_L = (int) Math.round(((20.0 / roundedR) + 1) * ((L_seekbarLenght_R - L_sekbarResult_R) * (L_seekbarLenght_R)));
                L_sekbarResult_R = (int) Math.round(((20.0 / L_double_R) + 1) * ((L_seekbarLenght_R - L_sekbarResult_R) * (L_seekbarLenght_R)));
            }

            if (bm == 1) {
                if (b == 1) {L_speed_R = R_speed_L = (L_speed_R + R_speed_L) / 2;}
                L_seekbar_R = R_seekbar_L = (L_seekbar_R + R_seekbar_L) / 2;
                L_sekbarResult_R = L_seekbar_R + R_seekbar_L;
                field_separation(L_double_R); // TODO Лінію переробити.
            }

            OLD_L_seekbar_R = L_seekbar_R; OLD_R_seekbar_L = R_seekbar_L; OLD_L_Int_and_R = L_sekbarResult_R;

            if (restore == 2) {
                L_seekbar_R = (int) Math.round(L_seekbar_R / 100.0 * L_percen_R);
                R_seekbar_L = (int) Math.round(R_seekbar_L / 100.0 * R_percen_L);
                L_sekbarResult_R = (int) Math.round(L_sekbarResult_R / 100.0 * L_percenAnd_R);
                L_position_R = (int) Math.round(L_seekbarLenght_R / 100.0 * L_percenPost_R);
                R_position_L = (int) Math.round(L_seekbarLenght_R / 100.0 * R_percenPost_L);
            } else {
                L_position_R = 0; R_position_L = 0; L_direct = true; R_direct = true;
            }

            left_seekBar_right.setMax(L_seekbarLenght_R); right_seekBar_left.setMax(L_seekbarLenght_R);
            left_seekBar_right.setProgress(L_position_R); right_seekBar_left.setProgress(R_position_L);
            left_direct_right.setMax(L_seekbarLenght_R); left_direct_right.setProgress(roundedL);

            editText_left.setText(String.valueOf(L_seekbar_R)); editText_right.setText(String.valueOf(R_seekbar_L));
            left_result_right.setText(String.valueOf(L_sekbarResult_R));
            System.out.println(L_seekbarLenght_R);
        }

        if (!move_on) {
            editText_left.setText(String.valueOf(Int_left));
            editText_right.setText(String.valueOf(Int_right));
            result_choice(); direction_choice(); seekbar_choice();
        }
    }

    public void field_separation (double L_double_R) {
        roundedL = (int) Math.round(Int_left / (L_double_R / L_seekbarLenght_R));
        roundedR = (int) Math.round(Int_right / (L_double_R / L_seekbarLenght_R));
        if (roundedL == 0 || roundedR == 0){ // Цей код можна впростити до простоти. Але мені нрав.
            if (roundedL == 0){
                roundedL = (int)Math.ceil(Int_left / (L_double_R / L_seekbarLenght_R));
                roundedR = (int)Math.floor(Int_right / (L_double_R / L_seekbarLenght_R));
            }
            if (roundedR == 0){
                roundedR = (int)Math.ceil(Int_right / (L_double_R / L_seekbarLenght_R));
                roundedL = (int)Math.floor(Int_left / (L_double_R / L_seekbarLenght_R));
            }
        } if (roundedL + roundedR > L_seekbarLenght_R) { L_seekbarLenght_R++; }
    }


    View.OnTouchListener click_defense_Left = (view, event) -> {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (b == 2){ // mechanical
                }

                if (b == 1){ // dynamicall
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        view.performClick();
        return false;
    };


    View.OnTouchListener click_defense_Right = (view, event) -> {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //some code....
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        view.performClick();
        return false;
    };


    public void click_flank (View view) {
        int id = view.getId();
        if (id == R.id.button_up_flank_Left) {

        } else if (id == R.id.button_center_flank_Left) {

        } else if (id == R.id.button_down_flank_Left) {

        } else if (id == R.id.button_up_flank_Right) {

        } else if (id == R.id.button_center_flank_Right) {

        } else if (id == R.id.button_down_flank_Right) {

        }
    }


    public void click_player (View view){ if (p == 3) { p = 2;} }


    public void click_modes (View view){
        final Handler handler = new Handler();
        if (!m_on){
            m_on = true; view_on = true; viwe_on_off();
            button_modes.setText(R.string.back);

            handler.postDelayed(() -> {
                if (m_on) { m_on = false; view_on = false; viwe_on_off(); button_modes.setText(R.string.battleline); }
            }, 1000);
        } else { m_on = false; button_modes.setEnabled(false);
            handler.postDelayed(() -> {
                //setRotationAnimation();
                Intent intent = new Intent(MainBattleline.this, MainActivity.class);
                startActivity(intent);
                finish();
            }, 250);
        }
    }


    public void click_direction_choice (View view) {
        if (p == 3 && L_direct_R != 3) {L_direct_R = 3;}
        else if (m != 2 && L_direct_R == 3) {L_direct_R = 0;}
        else if (m != 2 && menu_on) {L_direct_R = 0;}
        direction_choice();
    }

    public void direction_choice() {
        if (L_direct_R == 0 || L_direct_R == 3){ left_direct_right.setProgressDrawable(ContextCompat.getDrawable(this, R.drawable.direct_default)); }
        if (L_direct_R == 1 || L_direct_R == 2) { left_direct_right.setProgress(1);
            left_direct_right.setProgressDrawable(ContextCompat.getDrawable(this, R.drawable.direct_action));
            if (Int_right != 0 && Final_right == 0 || Int_left != 0 && Final_left == 0) { left_direct_right.setProgressDrawable(ContextCompat.getDrawable(this, R.drawable.direct_fatality)); } }
        if (L_direct_R == 0){ left_direct_right.setProgress(0);  } // Left_and_Right && (Int_left != 0 || Int_right != 0)
        if (L_direct_R == 2){ left_direct_right.setRotation(-180);} // Left_to_Right
        if (L_direct_R == 1){ left_direct_right.setRotation(0); } // Right_to_Left
        if (L_direct_R == 3){ left_direct_right.setProgress(30); } // Left_peace_Right
    }

    public void seekbar_choice() { // TODO Відповідає за онулення позиції гравців. Як що рестор не 0, то показати старе положення.
        if (restore == 2) {
            left_seekBar_right.setMax(L_seekbarLenght_R); right_seekBar_left.setMax(L_seekbarLenght_R);
            left_seekBar_right.setProgress(L_position_R); right_seekBar_left.setProgress(R_position_L);
        } else {
            left_seekBar_right.setProgress(0);
            right_seekBar_left.setProgress(0);
        }
    }

    View.OnLongClickListener long_direction_choice = v -> {
        if (menu_on){
            L_position_R = 0; R_position_L = 0; L_direct = true; R_direct = true; seekbar_choice();
        }
        return true;
    };


    public void result_choice () {
        if (Int_left == 0 || Int_right == 0) { L_result_R = 0; }
        if (L_direct_R != 3 && L_result_R != 0){
            left_result_right.setText(String.valueOf(L_result_R));
            left_result_right.setVisibility(View.VISIBLE);
        } else {left_result_right.setVisibility(View.GONE);
            if (!menu_on && L_direct_R != 3){L_direct_R = 0;}}
    }


    public void menu_visible () { // TODO Відповідає за скриття опційного меню
        if (!menu_on) { // Як що меню виключине то:
            view_on = true; viwe_on_off();
            linear_menu.setVisibility(View.GONE);
            button_clear.setText(R.string.stop);
            button_clear.getLayoutParams().height = (int)
                    (button_clear.getResources().getDisplayMetrics().density * 35);
        } else {
            view_on = false; viwe_on_off();
            linear_menu.setVisibility(View.VISIBLE);
            button_clear.setText(R.string.clear);
            button_clear.getLayoutParams().height = (int)
                    (button_clear.getResources().getDisplayMetrics().density * 50);
        }
    }


    public void viwe_on_off () {
        if (!view_on){
            button_clear.setEnabled(true); button_count.setEnabled(true);
            button_basis.setEnabled(true); button_player.setEnabled(true);
            button_modes.setEnabled(true);

            editText_left.setEnabled(true);
            editText_right.setEnabled(true);
            left_direct_right.setEnabled(true);

            editText_check();
        } else {
            if(menu_on){button_clear.setEnabled(false);} if (!c_on){button_count.setEnabled(false);}
            if (!b_on){button_basis.setEnabled(false);} button_action.setEnabled(false);
            button_player.setEnabled(false); if (!m_on){button_modes.setEnabled(false);}

            editText_left.setEnabled(false);
            editText_right.setEnabled(false);
            if (menu_on) {left_direct_right.setEnabled(false);}
        }
    }


    public void editText_check () {
        if (!StartData) {

            string_left = editText_left.getText().toString().trim();
            string_right = editText_right.getText().toString().trim();
            if (k == 1 && !menu_on && !move_on) {
                string_left_right = editText_left_right.getText().toString().trim();
                string_left_down = editText_left_down.getText().toString().trim();
                string_right_up = editText_right_up.getText().toString().trim();
            }
        }

        if (k == 1 && !menu_on && !move_on) {
            if (!string_left.isEmpty() && !string_left_down.isEmpty() && !string_left_right.isEmpty() && p == 2) {
                button_move_Left.setEnabled(true);
            } else { button_move_Left.setEnabled(false); }

            if (!string_right.isEmpty() && !string_right_up.isEmpty() && !string_left_right.isEmpty() && p == 2) {
                button_move_Right.setEnabled(true);
            } else { button_move_Right.setEnabled(false); }
        } else {
            if (!string_left.isEmpty() && !string_right.isEmpty() && p == 2 && (!view_on || !menu_on)) {
                button_action.setEnabled(true);
            } else {
                button_action.setEnabled(false);
                if (menu_on) {L_result_C = 0;}
            }
        }
    }

    public void editText_Integer () {
        if (!string_left.isEmpty()) { Int_left = Integer.parseInt(string_left); }
        if (!string_right.isEmpty()) { Int_right = Integer.parseInt(string_right); }
    }


    public void close_cursor (){
        if (!c_on && !b_on) {
            launcher_background.requestFocusFromTouch();
            InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(launcher_background.getWindowToken(), 0);
        }

        if (c_on) {
            if (c == 3){button_count.setText(R.string.notcount);}
            else if (c == 2) {button_count.setText(R.string.negative);}
            else if (c == 1) {button_count.setText(R.string.positive);}
            else {button_count.setText(R.string.count);}

            c_on = false;
            if (!StartData){
                linear_Right_num_change.setVisibility(View.GONE);
                linear_Left_num_change.setVisibility(View.GONE);
                button_notcount.setVisibility(View.GONE);
                textView_stiffcount.setVisibility(View.GONE);
                button_stiffLeft.setVisibility(View.GONE);
                button_stiffRight.setVisibility(View.GONE);
                linear_count_choice.setVisibility(View.GONE);
                button_clear.setVisibility(View.VISIBLE);
                button_count.getLayoutParams().width = (int)
                        (button_count.getResources().getDisplayMetrics().density * 100);
                view_on = false; viwe_on_off();
            }
        }

        if (b_on) {
            if (b == 2) {button_basis.setText(R.string.mechanical);}
            else if (b == 1) {button_basis.setText(R.string.dynamicall);}
            else {button_basis.setText(R.string.basis);}

            if (k == 1){button_action.setText(R.string.creative);
            } else {button_action.setText(R.string.action);}

            b_on = false;
            if (!StartData){
                textView_basisofmode.setVisibility(View.GONE);
                linear_basisofmode_choice.setVisibility(View.GONE);
                button_basis.setVisibility(View.VISIBLE);
                linear_basis_choice.setVisibility(View.GONE);
                button_clear.setVisibility(View.VISIBLE);
                button_creative.setVisibility(View.GONE);
                button_count.getLayoutParams().height = (int)
                        (button_count.getResources().getDisplayMetrics().density * 50);
                button_count.setTextSize(14);
                view_on = false; viwe_on_off();
            }
        }
    }


    @Override
    public void onBackPressed() {
        if (menu_on){
            if (system_back_time + 2000 > System.currentTimeMillis()){
                super.onBackPressed();
            } system_back_time = System.currentTimeMillis();
        }
    }

    @Override
    protected void onPause() {
        saveData();
        super.onPause();
    }


    public void saveData () {
        SharedPreferences.Editor editMainData = MainDataExchange.edit();
        editMainData.putString("languageCode", languageCode);

        editMainData.putString("string_left", string_left); editMainData.putString("string_center", string_center); editMainData.putString("string_right", string_right);
        editMainData.putBoolean("L_direct", L_direct); editMainData.putBoolean("R_direct", R_direct);
        editMainData.putInt("Int_left", Int_left); editMainData.putInt("Int_center", Int_center); editMainData.putInt("Int_right", Int_right);
        editMainData.putInt("L_result_C", L_result_C); editMainData.putInt("L_result_R", L_result_R); editMainData.putInt("C_result_R", C_result_R);
        editMainData.putInt("L_seekbar_R", L_seekbar_R); editMainData.putInt("R_seekbar_L", R_seekbar_L);
        editMainData.putInt("L_position_R", L_position_R); editMainData.putInt("R_position_L", R_position_L);
        editMainData.putInt("L_speed_R", L_speed_R); editMainData.putInt("R_speed_L", R_speed_L);
        editMainData.putInt("L_seekbarLenght_R", L_seekbarLenght_R); editMainData.putInt("L_sekbarResult_R", L_sekbarResult_R);
        editMainData.putInt("OLD_L_seekbar_R", OLD_L_seekbar_R); editMainData.putInt("OLD_R_seekbar_L", OLD_R_seekbar_L); editMainData.putInt("OLD_L_Int_and_R", OLD_L_Int_and_R);
        editMainData.putInt("m", m); editMainData.putInt("c", c); editMainData.putInt("p", p); editMainData.putInt("b", b); editMainData.putInt("k", k);
        editMainData.putInt("L_direct_C", L_direct_C); editMainData.putInt("L_direct_R", L_direct_R); editMainData.putInt("C_direct_R", C_direct_R);
        editMainData.putInt("restore", restore); editMainData.putInt("bm", bm); editMainData.putInt("sc", (int) sc);

        editMainData.apply(); //editMainData.commit();
    }

    public void outData () {
        languageCode = MainDataExchange.getString("languageCode", languageCode);

        string_left = MainDataExchange.getString("string_left", string_left) ; string_center = MainDataExchange.getString("string_center", string_center); string_right = MainDataExchange.getString("string_right", string_right) ;
        L_direct = MainDataExchange.getBoolean("L_direct", L_direct); R_direct = MainDataExchange.getBoolean("R_direct", R_direct);
        Int_left = MainDataExchange.getInt("Int_left", Int_left); Int_center = MainDataExchange.getInt("Int_center", Int_center); Int_right = MainDataExchange.getInt("Int_right", Int_right);
        L_result_C = MainDataExchange.getInt("L_result_C", L_result_C); L_result_R = MainDataExchange.getInt("L_result_R", L_result_R); C_result_R = MainDataExchange.getInt("C_result_R", C_result_R);
        L_seekbar_R = MainDataExchange.getInt("L_seekbar_R", L_seekbar_R); R_seekbar_L = MainDataExchange.getInt("R_seekbar_L", R_seekbar_L);
        L_position_R = MainDataExchange.getInt("L_position_R", L_position_R); R_position_L = MainDataExchange.getInt("R_position_L", R_position_L);
        L_speed_R = MainDataExchange.getInt("L_speed_R", L_speed_R); R_speed_L = MainDataExchange.getInt("R_speed_L", R_speed_L);
        L_seekbarLenght_R = MainDataExchange.getInt("L_seekbarLenght_R", L_seekbarLenght_R); L_sekbarResult_R = MainDataExchange.getInt("L_sekbarResult_R", L_sekbarResult_R);
        OLD_L_seekbar_R = MainDataExchange.getInt("OLD_L_seekbar_R", OLD_L_seekbar_R); OLD_R_seekbar_L = MainDataExchange.getInt("OLD_R_seekbar_L", OLD_R_seekbar_L); OLD_L_Int_and_R = MainDataExchange.getInt("OLD_L_Int_and_R", OLD_L_Int_and_R);
        m = (byte) MainDataExchange.getInt("m", m); c = (byte) MainDataExchange.getInt("c", c); p = (byte) MainDataExchange.getInt("p", p); b = (byte) MainDataExchange.getInt("b", b); k = (byte) MainDataExchange.getInt("k", k);
        L_direct_C = (byte) MainDataExchange.getInt("L_direct_C", L_direct_C); L_direct_R = (byte) MainDataExchange.getInt("L_direct_R", L_direct_R); C_direct_R = (byte) MainDataExchange.getInt("C_direct_R", C_direct_R);
        restore = (byte) MainDataExchange.getInt("restore", restore); bm = (byte) MainDataExchange.getInt("bm", bm); sc = MainDataExchange.getInt("sc", (int) sc);

        if (b == 0) {
            Handler handler = new Handler(); view_on = true; viwe_on_off();
            handler.postDelayed(() -> { view_on = false; viwe_on_off(); }, 350); // 1050
        } else {anim_base_choice();}

        editText_left.setText(string_left); editText_right.setText(string_right);
        localization(languageCode);
        button_player.callOnClick(); c_on = true; b_on = true; close_cursor();
        result_choice(); seekbar_choice(); direction_choice();

        StartData = false;
    }
}