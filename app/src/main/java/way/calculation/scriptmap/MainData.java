package way.calculation.scriptmap;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainData extends AppCompatActivity {

    public Button button_clear, button_battleline, button_player, button_accident, button_stiffLeft,
            button_count, button_stiffRight, button_modes, button_notcount, button_positive, button_negative, button_action, button_direction;
    public Button button_dynamicall, button_mechanical, button_basisofmodeLeft, button_basis, button_basisofmodeRight,
            button_attack_Left, button_attack_Right, button_move_Left, button_move_Right,
            button_up_flank_Left, button_center_flank_Left, button_down_flank_Left, button_up_flank_Right, button_center_flank_Right, button_down_flank_Right,
            button_defense_Left, button_defense_Right;

    public ToggleButton button_restore;

    public LinearLayout linear_Left_Center_Right, linear_count_choice, linear_modes_count_choice, linear_accident_player; // Це поверхність, на яку можна помістити контент, і управляти ним як окремою частиною.
    public LinearLayout linear_basis_choice, linear_basisofmode_choice, linear_menu, linear_flank_Left, linear_flank_Right;

    public EditText editText_left, editText_center, editText_right; // Це поля в які можна щось писати.

    public TextView textView_stiffcount, left_direct_center, left_direct_right, center_direct_right, // Це їх аналоги, але без змоги воду.
            left_result_center, left_result_right, center_result_right;
    public TextView textView_basisofmode;

    public SeekBar left_seekBarAnd_right, left_seekBar_right, right_seekBar_left;

    public FrameLayout frameLayout_seekBar;
    public RelativeLayout launcher_background; // Це мабуть підключив заднік.



    public static final String APP_PREFERENCES = "MainData";
    public static SharedPreferences MainDataExchange;
    public Bundle arguments;
    public String languageCode = "default";



    public long system_clear_time, system_back_time; // Це невеличкі переміні які зберігають час кліка, в деяких функціях.
    public long system_start_Left_time, system_start_Right_time;

    public String string_left = "", string_center = "", string_right = ""; // Це переміні які хранять текст, який я провіряю на пустоту, бо як конверсія з пустої строки в переміну визве помилку.

    public boolean view_on = false, c_on = false, m_on = false, menu_on = true; // Це переміні які я використовую як переключатілі на кнопках. v_on = Viwe_on, c_on = Count_on, m_on = Mode_on, d_long_on = Direct_long_on.
    public boolean b_on = false, move_on = false, L_direct = true, R_direct = true;

    public int Final_left, Final_center, Final_right;
    public int Int_left, Int_center, Int_right, L_result_C, L_result_R, C_result_R;
    public int L_seekbar_R, R_seekbar_L, L_position_R, R_position_L, L_speed_R, R_speed_L, L_seekbarLenght_R, L_sekbarResult_R,
            OLD_L_seekbar_R, OLD_R_seekbar_L, OLD_L_Int_and_R;

    public byte m, c, p = 2, L_direct_C, L_direct_R, C_direct_R, b; // Це скороченя від m = Mode, c = Count, p = Player, b = Basis, bm = BasisMode
    public double sc = 10; // Ця переміна має в собі ключове число яке впливає на результат сили атаки.
    public byte restore, bm = 2; // R_vector


    public double L_percen_R, R_percen_L, L_percenAnd_R, L_percenPost_R, R_percenPost_L;
}



