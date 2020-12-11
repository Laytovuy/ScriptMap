package way.calculation.scriptmap;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;


public class MainBattleline extends MainActivity {

    public Button button_clear, button_dynamicall, button_mechanical, button_positive, button_notcount,
            button_negative, button_stiffLeft, button_count,
            button_stiffRight, button_basisofmodeLeft, button_basis, button_basisofmodeRight,button_action, button_player, button_modes,
            button_attack_Left, button_attack_Right, button_move_Left, button_move_Right,
            button_up_flank_Left, button_center_flank_Left, button_down_flank_Left, button_up_flank_Right, button_center_flank_Right, button_down_flank_Right,
            button_defense_Left, button_defense_Right;
    private LinearLayout linear_count_choice, linear_basis_choice, linear_basisofmode_choice, linear_menu, linear_flank_Left, linear_flank_Right;
    private EditText editText_left, editText_right;
    public TextView  left_result_right, textView_stiffcount, textView_basisofmode, left_direct_right;
    public SeekBar left_seekBarAnd_right, left_seekBar_right, right_seekBar_left;
    public RelativeLayout launcher_background;
    private long system_start_Left_time, system_start_Right_time, system_back_time;

    public String string_left, string_right;
    public int Int_left = 0, Int_right = 0, L_Int_and_R, L_seekbarAnd_R, L_seekbar_R, R_seekbar_L;
    public byte b = 0, bm = 2, m = 4, c = 0, p = 2, L_direct_R = 0;
    public double sc = 10;
    public boolean v_on = false, m_on = false, c_on = false, b_on = false,  menu_on = false, d_long_on = false, move_on = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_battleline_land);
        // TODO create get intent
        // TODO STOP battleline
        // TODO Скрол створений не підходить на кнопки.
        // Також я думаю над тим чи змінити всю орінтацію застосунку на горизонтальну...

        button_clear = findViewById(R.id.button_clear);
        button_dynamicall = findViewById(R.id.button_dynamicall);
        button_mechanical = findViewById(R.id.button_mechanical);
        button_positive = findViewById(R.id.button_positive);
        button_notcount = findViewById(R.id.button_notcount);
        button_negative = findViewById(R.id.button_negative);
        button_stiffLeft = findViewById(R.id.button_stiffLeft);
        button_count = findViewById(R.id.button_count);
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

        linear_count_choice = findViewById(R.id.linear_count_choice);
        linear_basisofmode_choice = findViewById(R.id.linear_basisofmode_choice);
        linear_basis_choice = findViewById(R.id.linear_basis_choice);
        linear_menu = findViewById(R.id.linear_menu);
        linear_flank_Left = findViewById(R.id.linear_flank_Left);
        linear_flank_Right = findViewById(R.id.linear_flank_Right);

        editText_left = findViewById(R.id.Left_EditText);
        editText_right = findViewById(R.id.Right_EditText);

        left_direct_right = findViewById(R.id.Left_direction_Right);
        left_direct_right.setOnLongClickListener(long_direction_choice);
        textView_basisofmode = findViewById(R.id.textView_basisofmode);
        textView_stiffcount = findViewById(R.id.textView_stiffcount);
        left_result_right = findViewById(R.id.Left_result_Right);

        left_seekBarAnd_right = findViewById(R.id.Left_seekBarAnd_Right);
        left_seekBar_right = findViewById(R.id.Left_seekBar_Right);
        right_seekBar_left = findViewById(R.id.Right_seekBar_Left);

        string_left = editText_left.getText().toString().trim();
        string_right = editText_right.getText().toString().trim();

        launcher_background = findViewById(R.id.launcher_background);
        launcher_background.setOnLongClickListener(long_click_launcher_background);
        button_clear.setOnLongClickListener(long_click_clear);

        editText_left.addTextChangedListener(editText_watcher);
        editText_right.addTextChangedListener(editText_watcher);

        // Як що основа режиму 3 "довічне" то, очки не будуть обчислюватись, але як що гравець не виставить очки, задається стандартна швидкість,
        // або як що вписати то швидкість буде виходити з цього показника.
    }


    private TextWatcher editText_watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {editText_check();}
        @Override
        public void afterTextChanged(Editable s) {}
    };


    View.OnLongClickListener long_click_launcher_background = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if (!menu_on && !m_on){
                button_clear.callOnClick();
                editText_right.setText(String.valueOf(50));
                editText_left.setText(String.valueOf(50));
            } else if (m_on) { // Пасхалка...
                launcher_background.setBackgroundResource(R.color.colorGrayDark);
            }
            return false;
        }
    };

    public void click_launcher_background (View view){ if (!menu_on) {close_cursor();}}


    public void click_clear (View view){
        if (!menu_on){
            L_seekbarAnd_R = 0; L_seekbar_R = 0; R_seekbar_L = 0;
            L_direct_R = 0;
            if (!d_long_on){
                editText_right.setText("");
                editText_left.setText("");
            } left_result_right.setVisibility(View.INVISIBLE);
            d_long_on = false;
            direction_choice(); seekbar_choice(); close_cursor();
        }
    }


    View.OnLongClickListener long_click_clear = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if (menu_on){
                move_on = true;
                menu_on = false; menu_on_off(); //TODO STOP battleline
            } else {
                b = 0; button_basis.setText(R.string.basis);
                c = 0; button_count.setText(R.string.count);
                sc = 10;
                bm = 2; anim_base_choice();
                v_on = false; viwe_on_off();
            }
            return true;
        }
    };


    public void click_count (View view){
        switch (view.getId()){
            case R.id.button_stiffLeft:
                sc += 2; if (sc > 20){ sc = 10; }
                break;
            case R.id.button_count:
                if (!c_on){
                    c_on = true; v_on = true; viwe_on_off();
                    button_clear.setVisibility(View.GONE);
                    button_count.getLayoutParams().width = (int)
                            (button_count.getResources().getDisplayMetrics().density * 28);

                    button_count.setText("");
                    button_notcount.setVisibility(View.VISIBLE);
                    textView_stiffcount.setVisibility(View.VISIBLE);
                    linear_count_choice.setVisibility(View.VISIBLE);
                    button_stiffLeft.setVisibility(View.VISIBLE);
                    button_stiffRight.setVisibility(View.VISIBLE);
                    button_notcount.setTextSize(8);
                } else {sc = 10; textView_stiffcount.setText(Html.fromHtml(getString(R.string.stiffprimary)));}
                break;
            case R.id.button_stiffRight:
                sc -= 2; if (sc < 0){ sc = 10; }
                break;
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

        close_cursor ();
    }


    public void click_basis (View view){
        switch (view.getId()){
            case R.id.button_basisofmodeLeft:
                if (bm > 2){ bm = 1;
                } else { bm --; if (bm < 1){ bm = 2; } }
                break;
            case R.id.button_basis:
                if (!b_on){
                    b_on = true; v_on = true; viwe_on_off();
                    textView_basisofmode.setVisibility(View.VISIBLE);
                    linear_basis_choice.setVisibility(View.VISIBLE);
                    linear_basisofmode_choice.setVisibility(View.VISIBLE);
                    button_basis.setVisibility(View.GONE);
                    button_clear.setVisibility(View.GONE);
                }
                break;
            case R.id.button_basisofmodeRight:
                if (bm < 2) { bm = 3;
                } else { bm ++; if (bm > 3){ bm = 2; } }
                break;
        }

        if (b_on) {
            if (bm == 3) {
                String string_isabsent = getString(R.string.isabsent);
                textView_basisofmode.setText(Html.fromHtml(getString(R.string.basisofmode, string_isabsent)));
            } else if (bm == 2) {
                String string_dependent = getString(R.string.dependent);
                textView_basisofmode.setText(Html.fromHtml(getString(R.string.basisofmode, string_dependent)));
            } else if (bm == 1) {
                String string_monotonous = getString(R.string.monotonous);
                textView_basisofmode.setText(Html.fromHtml(getString(R.string.basisofmode, string_monotonous)));}
            textView_basisofmode.setTextSize(12);
        }
    }


    public void click_base_choice (View view){
        switch (view.getId()){
            case R.id.button_mechanical:
                b = 2;
                button_basis.setText(R.string.mechanical);
                break;
            case R.id.button_dynamicall:
                b = 1;
                button_basis.setText(R.string.dynamicall);
                break;
        }

        close_cursor ();
        direction_choice(); anim_base_choice();
    }


    public void anim_base_choice() {
        button_move_Left.setVisibility(View.GONE); button_move_Right.setVisibility(View.GONE);
        linear_flank_Left.setVisibility(View.GONE); linear_flank_Right.setVisibility(View.GONE);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() { button_attack_Left.setVisibility(View.GONE); button_attack_Right.setVisibility(View.GONE);
                button_defense_Left.setVisibility(View.GONE); button_defense_Right.setVisibility(View.GONE); }
        }, 100);

        if (b == 2){ // mechanical
            v_on = true; viwe_on_off();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    editText_left.setVisibility(View.GONE); editText_right.setVisibility(View.GONE);
                }
            }, 500);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    button_attack_Left.setVisibility(View.VISIBLE); button_attack_Right.setVisibility(View.VISIBLE);
                    button_defense_Left.setVisibility(View.VISIBLE); button_defense_Right.setVisibility(View.VISIBLE);
                }
            }, 700);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    editText_left.setVisibility(View.VISIBLE); editText_right.setVisibility(View.VISIBLE);
                }
            }, 900);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    button_move_Left.setVisibility(View.VISIBLE); button_move_Right.setVisibility(View.VISIBLE);
                    linear_flank_Left.setVisibility(View.VISIBLE); linear_flank_Right.setVisibility(View.VISIBLE);
                    v_on = false; viwe_on_off();
                }
            }, 1050);
        }

        if (b == 1) { // dynamicall
            v_on = true; viwe_on_off();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    editText_left.setVisibility(View.GONE); editText_right.setVisibility(View.GONE);
                }
            }, 500);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    button_attack_Left.setVisibility(View.VISIBLE); button_attack_Right.setVisibility(View.VISIBLE);
                    button_defense_Left.setVisibility(View.VISIBLE); button_defense_Right.setVisibility(View.VISIBLE);
                }
            }, 700);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    editText_left.setVisibility(View.VISIBLE); editText_right.setVisibility(View.VISIBLE);
                }
            }, 900);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    button_attack_Left.setVisibility(View.GONE); button_attack_Right.setVisibility(View.GONE);
                    button_defense_Left.setVisibility(View.GONE); button_defense_Right.setVisibility(View.GONE);
                    button_move_Left.setVisibility(View.VISIBLE); button_move_Right.setVisibility(View.VISIBLE);
                    linear_flank_Left.setVisibility(View.VISIBLE); linear_flank_Right.setVisibility(View.VISIBLE);
                    v_on = false; viwe_on_off();
                }
            }, 1050);
        }
    }


    public void click_action (View view){
        if (!string_left.isEmpty()) { Int_left = Integer.parseInt(string_left);}
        if (!string_right.isEmpty()) { Int_right = Integer.parseInt(string_right);}

        if (b == 0 || c == 0 && bm != 3){
            if (b == 0){button_basis.setEnabled(false);}
            if (c == 0 && bm != 3){button_count.setEnabled(false);}

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (b == 0){button_basis.setEnabled(true);}
                    if (c == 0 && bm != 3){button_count.setEnabled(true);}
                }
            }, 250);

        } else {
            if (Int_left != 0 && Int_right != 0){
                menu_on = true; menu_on_off();
                direction_choice();
            } else {
                if (Int_left == 0){editText_left.setText("");}
                if (Int_right == 0){ editText_right.setText("");}
            }
        }
    }


    public void menu_on_off () {
        if (menu_on) {
            v_on = true; viwe_on_off();
            linear_menu.setVisibility(View.GONE);
            button_clear.setText(R.string.stop);
            button_clear.getLayoutParams().height = (int)
                    (button_clear.getResources().getDisplayMetrics().density * 35);

            button_move_Left.setEnabled(true);
            button_move_Right.setEnabled(true);
        } else {
            v_on = false; viwe_on_off();
            linear_menu.setVisibility(View.VISIBLE);
            button_clear.setText(R.string.clear);
            button_clear.getLayoutParams().height = (int)
                    (button_clear.getResources().getDisplayMetrics().density * 50);

            button_move_Left.setEnabled(false);
            button_move_Right.setEnabled(false);
            check_start();
        }
    }


    public void click_player (View view){ }


    View.OnTouchListener click_attack_Left = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_UP:
                    break;
            }
            view.performClick();
            return false;
        }
    };


    View.OnTouchListener click_attack_Right = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_UP:
                    break;
            }
            view.performClick();
            return false;
        }
    };


    View.OnTouchListener click_move_Left = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (move_on) {


                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (!move_on) {
//                        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//                        vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
                        button_move_Left.setEnabled(false);
                        system_start_Left_time = System.currentTimeMillis();
                        check_start();

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (!move_on && menu_on){
                                    button_move_Left.setEnabled(true);
                                }
                            }
                        }, 1200);
                    }
                    break;
            }
            view.performClick();
            return false;
        }
    };

    View.OnLongClickListener long_click_move_Left = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if (move_on){}
            return true;
        }
    };


    View.OnTouchListener click_move_Right = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (move_on) {

                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (!move_on) {
//                        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//                        vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
                        button_move_Right.setEnabled(false);
                        system_start_Right_time = System.currentTimeMillis();
                        check_start();

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (!move_on && menu_on){
                                    button_move_Right.setEnabled(true);
                                }
                            }
                        }, 1200);
                    }
                    break;
            }
            view.performClick();
            return false;
        }
    };

    View.OnLongClickListener long_click_move_Right = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if (move_on){}
            return true;
        }
    };


    public void check_start() {
        if (!move_on) {
            if (system_start_Left_time + 120 > System.currentTimeMillis() && system_start_Right_time + 120 > System.currentTimeMillis()){
                if (b == 2) {
                    button_attack_Left.setEnabled(true);
                    button_attack_Right.setEnabled(true);
                    button_defense_Left.setEnabled(true);
                    button_defense_Right.setEnabled(true);
                } else if (b == 1) {}
                button_up_flank_Left.setEnabled(true);
                button_center_flank_Left.setEnabled(true);
                button_down_flank_Left.setEnabled(true);
                button_up_flank_Right.setEnabled(true);
                button_center_flank_Right.setEnabled(true);
                button_down_flank_Right.setEnabled(true);
                left_direct_right.setText("");
                left_result_right.setVisibility(View.VISIBLE);
                left_seekBarAnd_right.setVisibility(View.VISIBLE);
                move_on = true; battleline_status();
            } // else {}
        } else {
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
            left_seekBarAnd_right.setVisibility(View.GONE);
            direction_choice();
            move_on = false;
        }
    }


    public void battleline_status() {

        L_Int_and_R = Int_left + Int_right; L_seekbarAnd_R = L_Int_and_R;
        double L_double_R = L_Int_and_R; int degree_LR, result_degree;
//TODO Працюю над цією частиною.
        if (L_seekbarAnd_R > 25) {
            for (degree_LR = 2, result_degree = 1 + L_seekbarAnd_R; result_degree > L_seekbarAnd_R; degree_LR ++) {
                result_degree = (L_seekbarAnd_R / degree_LR * (L_seekbarAnd_R / degree_LR));
            } L_seekbarAnd_R = degree_LR + 19;
        }

        if (b == 2){ // mechanical
            L_seekbar_R = ((L_Int_and_R / Int_left) + L_seekbarAnd_R) * 2;
            R_seekbar_L = ((L_Int_and_R / Int_right) + L_seekbarAnd_R) * 2;
            L_Int_and_R = (L_seekbar_R + R_seekbar_L + L_seekbarAnd_R) / 2;
        }

        if (b == 1){ // dynamicall
            L_seekbar_R = (int) Math.round(Int_left / (L_double_R / L_seekbarAnd_R)); // Speed_left
            R_seekbar_L = (int) Math.round(Int_right / (L_double_R / L_seekbarAnd_R)); // Speed_right
            for (degree_LR = 0, result_degree = 0; result_degree < 200; degree_LR ++){
                result_degree = L_seekbarAnd_R * degree_LR;
                L_Int_and_R = result_degree - degree_LR;
            } L_seekbarAnd_R += L_Int_and_R;
            L_Int_and_R = (int) Math.round(((20 / L_double_R) + 1) * ((L_seekbarAnd_R - L_Int_and_R) * (L_seekbarAnd_R)));
        }

        int roundedR, roundedL;
        roundedL = (int) Math.round(Int_left / (L_double_R / L_seekbarAnd_R));
        roundedR = (int) Math.round(Int_right / (L_double_R / L_seekbarAnd_R));
        if (roundedL + roundedR > L_seekbarAnd_R) { L_seekbarAnd_R ++; }

        left_seekBar_right.setMax(L_seekbarAnd_R); right_seekBar_left.setMax(L_seekbarAnd_R);
        left_seekBarAnd_right.setMax(L_seekbarAnd_R); left_seekBarAnd_right.setProgress(roundedL);
        editText_left.setText(String.valueOf(L_seekbar_R)); editText_right.setText(String.valueOf(R_seekbar_L));
        left_result_right.setText(String.valueOf(L_Int_and_R));
    }


    View.OnTouchListener click_defense_Left = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    //some code....
                    break;
                case MotionEvent.ACTION_UP:
                    break;
            }
            view.performClick();
            return false;
        }
    };


    View.OnTouchListener click_defense_Right = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    //some code....
                    break;
                case MotionEvent.ACTION_UP:
                    break;
            }
            view.performClick();
            return false;
        }
    };


    public void click_flank (View view) {
        switch (view.getId()){
            case R.id.button_up_flank_Left:
                break;
            case R.id.button_center_flank_Left:
                break;
            case R.id.button_down_flank_Left:
                break;
            case R.id.button_up_flank_Right:
                break;
            case R.id.button_center_flank_Right:
                break;
            case R.id.button_down_flank_Right:
                break;
        }
    }


    public void click_modes (View view){
        final Handler handler = new Handler();
        if (!m_on){
            m_on = true; v_on = true; viwe_on_off();
            button_modes.setText(R.string.back);
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(MainBattleline.this, MainActivity.class);
                    intent.putExtra("string_left", string_left);
                    intent.putExtra("string_center", string_center);
                    intent.putExtra("string_right", string_right);

                    intent.putExtra("sn", sc);
                    intent.putExtra("c", c);
                    //intent.putExtra("p", p);
                    intent.putExtra("b", b);
                    intent.putExtra("bm", bm);
                    if (L_direct_C == 3) {intent.putExtra("L_direct_C", L_direct_C);}
                    if (L_direct_R == 3) {intent.putExtra("L_direct_R", L_direct_R);}
                    if (C_direct_R == 3) {intent.putExtra("C_direct_R", C_direct_R);}
                    //startActivity(intent);
                    onBackPressed();
                    finish();
                }
            }, 250);
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                m_on = false; v_on = false; viwe_on_off(); button_modes.setText(R.string.battleline);
            }
        }, 1000);
    }


    public void click_direction_choice (View view) {
        if (p == 3 && L_direct_R != 3) {L_direct_R = 3;}
        else if (L_direct_R == 3) {L_direct_R = 0;}
        direction_choice();
    }


    public void direction_choice() {
        if (L_direct_R == 0){ left_direct_right.setText(R.string.Left_and_Right_rotate);}
        if (L_direct_R == 2){ left_direct_right.setText(R.string.Left_to_Right_rotate);}
        if (L_direct_R == 1){ left_direct_right.setText(R.string.Right_to_Left_rotate);}
        if (L_direct_R == 3){ left_direct_right.setText(R.string.Left_peace_Right_rotate);}
    }


    public void seekbar_choice() {
        left_seekBarAnd_right.setProgress(L_seekbarAnd_R);
        left_seekBar_right.setProgress(L_seekbar_R);
        right_seekBar_left.setProgress(R_seekbar_L);
    }


    View.OnLongClickListener long_direction_choice = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if (!menu_on){
                d_long_on = true;
                button_clear.callOnClick();
            }
            return true;
        }
    };


    public void viwe_on_off () {
        if (!v_on){
            button_clear.setEnabled(true); if (bm != 3){button_count.setEnabled(true);}
            button_basis.setEnabled(true); button_player.setEnabled(true);
            button_modes.setEnabled(true);

            editText_left.setEnabled(true);
            editText_right.setEnabled(true);
            left_direct_right.setEnabled(true);

            editText_check();
        } else {
            if(!menu_on){button_clear.setEnabled(false);} if (!c_on){button_count.setEnabled(false);}
            if (!b_on){button_basis.setEnabled(false);} button_action.setEnabled(false);
            button_player.setEnabled(false); if (!m_on){button_modes.setEnabled(false);}

            editText_left.setEnabled(false);
            editText_right.setEnabled(false);
            left_direct_right.setEnabled(false);
        }
    }


    public void editText_check () {
        string_left = editText_left.getText().toString().trim();
        string_right = editText_right.getText().toString().trim();

        if (!string_left.isEmpty() && !string_right.isEmpty() && p == 2) {
            button_action.setEnabled(true);
        } else {
            if (bm == 3){ button_action.setEnabled(true);
            } else { button_action.setEnabled(false);}
        }
    }


    public void close_cursor (){
        if (!c_on && !b_on) {
            editText_left.setEnabled(false); editText_right.setEnabled(false);
            editText_left.setEnabled(true); editText_right.setEnabled(true);
        }

        if (c_on) {
            if (c == 3){ button_count.setText(R.string.notcount); }
            else if (c == 2) { button_count.setText(R.string.negative); }
            else if (c == 1) { button_count.setText(R.string.positive); }
            else { button_count.setText(R.string.count); }
            button_count.getLayoutParams().width = (int)
                    (button_count.getResources().getDisplayMetrics().density * 100);

            button_notcount.setVisibility(View.GONE);
            textView_stiffcount.setVisibility(View.GONE);
            button_stiffLeft.setVisibility(View.GONE);
            button_stiffRight.setVisibility(View.GONE);
            linear_count_choice.setVisibility(View.GONE);
            button_clear.setVisibility(View.VISIBLE);
            c_on = false; v_on = false; viwe_on_off();
        }

        if (b_on) {
            if (b == 2) { button_basis.setText(R.string.mechanical); }
            else if (b == 1) { button_basis.setText(R.string.dynamicall); }
            else { button_basis.setText(R.string.basis); }

            textView_basisofmode.setVisibility(View.GONE);
            linear_basisofmode_choice.setVisibility(View.GONE);
            button_basis.setVisibility(View.VISIBLE);
            linear_basis_choice.setVisibility(View.GONE);
            button_clear.setVisibility(View.VISIBLE);
            b_on = false; v_on = false; viwe_on_off();
        }
    }


    @Override
    public void onBackPressed() {
        if (!menu_on){
            if (system_back_time + 2000 > System.currentTimeMillis()){
                m_on = true; button_modes.callOnClick();
            } system_back_time = System.currentTimeMillis();
        }
    }
}