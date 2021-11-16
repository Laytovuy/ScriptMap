package way.calculation.scriptmap;

import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainData extends AppCompatActivity {

    public Button button_clear, button_battleline, button_player, button_accident, button_stiffLeft,
            button_count, button_stiffRight, button_modes, button_notcount, button_positive, button_negative, button_action, button_direction;
    public Button button_dynamicall, button_mechanical, button_creative, button_basisofmodeLeft, button_basis, button_basisofmodeRight,
            button_attack_Left, button_attack_Right, button_move_Left, button_move_Right,
            button_up_flank_Left, button_center_flank_Left, button_down_flank_Left, button_up_flank_Right, button_center_flank_Right, button_down_flank_Right,
            button_defense_Left, button_defense_Right;

    public ToggleButton button_continue;

    public LinearLayout linear_Right_num_change, linear_Center_num_change, linear_Left_num_change, linear_Left_Right_num_change;
    public LinearLayout linear_Left_Center_Right, linear_count_choice, linear_modes_count_choice, linear_accident_player; // Це поверхність, на яку можна помістити контент, і управляти ним як окремою частиною.
    public LinearLayout linear_basis_choice, linear_basisofmode_choice, linear_menu, linear_flank_Left, linear_flank_Right;

    public EditText editText_left, editText_center, editText_right; // Це поля в які можна щось писати.
    public EditText  editText_left_down, editText_left_and_right, editText_right_up, editText_left_down_right;

    public TextView textView_stiffcount, // Це їх аналоги, але без змоги воду.
            left_result_center, left_result_right, center_result_right;
    public TextView textView_basisofmode;

    public ProgressBar left_direct_center, left_direct_right, center_direct_right;
    public SeekBar left_seekBar_right, right_seekBar_left;

    public FrameLayout frameLayout_seekBar;
    public RelativeLayout launcher_background; // Це мабуть підключив заднік.



    public static final String APP_PREFERENCES = "MainData";
    public static SharedPreferences MainDataExchange;
    public String languageCode = "default";
    public boolean StartData = true;


    public long system_clear_time, system_back_time; // Це невеличкі переміні які зберігають час кліка, в деяких функціях.
    public long system_start_Left_time, system_start_Right_time;

    public String string_left = "", string_center = "", string_right = ""; // Це переміні які хранять текст, який я провіряю на пустоту, бо як конверсія з пустої строки в переміну визве помилку.
    public String string_right_up = "", string_left_down = "", string_left_and_right = "", string_left_down_right = "";
    public String string_left_front = "center", string_right_front = "center";
    public String string_L_activity_R = "", string_R_activity_L = "";

    public boolean view_on = false, c_on = false, m_on = false, menu_on = true; // Це переміні які я використовую як переключатілі на кнопках. v_on = Viwe_on, c_on = Count_on, m_on = Mode_on, d_long_on = Direct_long_on.
    public boolean b_on = false, move_on = false, L_direct = true, R_direct = true;


    public int Final_left, Final_center, Final_right;
    public int Int_left, Int_center, Int_right, L_result_C, L_result_R, C_result_R;
    public int L_seekbar_R, R_seekbar_L, L_position_R, R_position_L, L_speed_R, R_speed_L, L_seekbarLenght_R, L_sekbarResult_R,
            OLD_L_seekbar_R, OLD_R_seekbar_L, OLD_L_Int_and_R, rounded_L, rounded_R;
    public int Int_left_cr, Int_right_cr, Int_left_down, Int_left_and_right, Int_right_up, Int_left_down_right;
    public int L_capture_R, L_protect_R;

    public byte m, c, p = 2, L_direct_C, L_direct_R, C_direct_R; // Це скороченя від m = Mode, c = Count, p = Player, b = Basis, bm = BasisMode
    public double sc = 10; // Ця переміна має в собі ключове число яке впливає на результат сили атаки.
    public double L_percen_R, R_percen_L, L_percenAnd_R, L_percenPost_R, R_percenPost_L;
    public byte restore, cr, b, bm = 2; // R_vector



    /*getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); setContentView(R.layout.activity_main);*/

    /*public void saveData () {
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
        editMainData.putInt("m", m); editMainData.putInt("c", c); editMainData.putInt("p", p); editMainData.putInt("b", b);
        editMainData.putInt("L_direct_C", L_direct_C); editMainData.putInt("L_direct_R", L_direct_R); editMainData.putInt("C_direct_R", C_direct_R);
        editMainData.putInt("restore", restore); editMainData.putInt("bm", bm); editMainData.putInt("sc", (int) sc);

        editMainData.apply(); editMainData.commit();
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
        m = (byte) MainDataExchange.getInt("m", m); c = (byte) MainDataExchange.getInt("c", c); p = (byte) MainDataExchange.getInt("p", p); b = (byte) MainDataExchange.getInt("b", b);
        L_direct_C = (byte) MainDataExchange.getInt("L_direct_C", L_direct_C); L_direct_R = (byte) MainDataExchange.getInt("L_direct_R", L_direct_R); C_direct_R = (byte) MainDataExchange.getInt("C_direct_R", C_direct_R);
        restore = (byte) MainDataExchange.getInt("restore", restore); bm = (byte) MainDataExchange.getInt("bm", bm); sc = MainDataExchange.getInt("sc", (int) sc);

        editText_left.setText(string_left); editText_center.setText(string_center); editText_right.setText(string_right);
        //localization(languageCode);
        button_player.callOnClick(); c_on = true; m_on = true; close_cursor();
        result_choice(); direction_choice();
    }*/

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle arguments = getIntent().getExtras();
        if(arguments != null){ setRotationAnimation(); outData(); getIntent().removeExtra("intent"); getIntent().removeCategory("intent"); arguments = getIntent().getExtras(); }
    }

    public void StartActivity2 (View view) {
        setRotationAnimation();
        Intent intent = new Intent(MainActivity.this, MainBattleline.class); // Це створення обєкту який містить в собі запуск Активиті.
        saveData(); intent.putExtra("intent", "intent");
        startActivity(intent);
    }*/
}



