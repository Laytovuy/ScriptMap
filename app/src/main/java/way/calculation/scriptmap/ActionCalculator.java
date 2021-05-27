package way.calculation.scriptmap;


import androidx.core.content.ContextCompat;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ActionCalculator extends MainData {

    public void Calculation () {

        if (m != 2 && m != 4){
            int RandL = 0, RandC = 0, RandR = 0; // Режими
            int RandUpL = 0, RandUpC = 0, RandUpR = 0;

            if (m == 1){  // Верогідність

                if (Int_left != 0){
                    RandUpL = ThreadLocalRandom.current().nextInt(0, 2 + Int_left);
                    RandL = ThreadLocalRandom.current().nextInt(0, 2 + Int_left);
                }

                if (Int_center != 0){
                    RandUpC = ThreadLocalRandom.current().nextInt(0, 2 + Int_center);
                    RandC = ThreadLocalRandom.current().nextInt(0, 2 + Int_center);
                }

                if (Int_right != 0){
                    RandUpR = ThreadLocalRandom.current().nextInt(0, 2 + Int_right);
                    RandR = ThreadLocalRandom.current().nextInt(0, 2 + Int_right);
                }

                // Як що два наші противники між собою не ведуть битву, ми не отримуємо бонусів
                if (Int_left != 0 && C_direct_R != 3){ RandL = RandL + (RandUpC / 2) + (RandUpR / 2);}
                if (Int_center != 0 && L_direct_R != 3){ RandC = RandC + (RandUpL / 2) + (RandUpR / 2);}
                if (Int_right != 0 && L_direct_C != 3){ RandR = RandR + (RandUpL / 2) + (RandUpC / 2);}
            }

            if (m == 3) { // Випадковість
                ThreadLocalRandom random = ThreadLocalRandom.current();
                if (Int_left != 0){
                    RandUpL = random.nextInt(1, 99);
                    RandL = RandUpL + 1;}

                if (Int_center != 0){
                    RandUpC = random.nextInt(1, 99);
                    RandC = RandUpC + 1;}

                if (Int_right != 0){
                    RandUpR = random.nextInt(1, 99);
                    RandR = RandUpR + 1;}
            }


            if (L_direct_C != 3 && Int_left != 0 && Int_center != 0){
                if (RandL > RandC) {
                    L_direct_C = 1;} else if (RandC > RandL){
                    L_direct_C = 2;} else { L_direct_C = 0; }
            }
            if (L_direct_R != 3 && Int_left != 0 && Int_right != 0){
                if (RandR > RandL) {
                    L_direct_R = 1;} else if (RandL > RandR){
                    L_direct_R = 2;} else { L_direct_R = 0; }
            }
            if (C_direct_R != 3 && Int_center != 0 && Int_right != 0){
                if (RandC > RandR) {
                    C_direct_R = 1;} else if (RandR > RandC){
                    C_direct_R = 2;} else { C_direct_R = 0; }
            }
        }

        double doubleL = Int_left, doubleC = Int_center, doubleR = Int_right; // Обчисленя

        if (L_direct_C != 3 && L_direct_C != 0 && Int_left != 0 && Int_center != 0){ // Обчисленя між Left і Center
            if (L_direct_C == 1) {
                if (sc != 0){
                    double L_double_C = (Int_left + Int_center) * ((doubleL / doubleC) / sc);
                    L_result_C = (int) Math.ceil(L_double_C);
                } else {L_result_C = Int_center;}
            }

            if (L_direct_C == 2) {
                if (sc != 0){
                    double L_double_C = (Int_center + Int_left) * ((doubleC / doubleL) / sc);
                    L_result_C = (int) Math.ceil(L_double_C);
                } else {L_result_C = Int_left;}
            }
        }

        if (L_direct_R != 3 && L_direct_R != 0 && Int_left != 0 && Int_right != 0){ // Обчисленя між Left і Right
            if (L_direct_R == 2) {
                if (sc != 0){
                    double L_double_R = (Int_left + Int_right) * ((doubleL / doubleR) / sc);
                    L_result_R = (int) Math.ceil(L_double_R);
                } else {L_result_R = Int_right;}
            }

            if (L_direct_R == 1) {
                if (sc != 0){
                    double L_double_R = (Int_right + Int_left) * ((doubleR / doubleL) / sc);
                    L_result_R = (int) Math.ceil(L_double_R);
                } else {L_result_R = Int_left;}
            }
        }

        if (C_direct_R != 3 && C_direct_R != 0 && Int_center != 0 && Int_right != 0){ // Обчисленя між Center і Right
            if (C_direct_R == 1) {
                if (sc != 0){
                    double C_double_R = (Int_center + Int_right) * ((doubleC / doubleR) / sc);
                    C_result_R = (int) Math.ceil(C_double_R);
                } else {C_result_R = Int_right;}
            }

            if (C_direct_R == 2) {
                if (sc != 0){
                    double C_double_R = (Int_right + Int_center) * ((doubleR / doubleC) / sc);
                    C_result_R = (int) Math.ceil(C_double_R);
                } else {C_result_R = Int_center;}
            }
        }


        Final_left = Int_left; Final_center = Int_center; Final_right = Int_right; // Сумування


        if (L_direct_C != 3 && L_direct_C != 0 && Int_left != 0 && Int_center != 0) { //  Сумування між Left і Center
            if (L_direct_C == 1 && C_direct_R != 2){
                if (Int_center > L_result_C){
                    if (c == 1){
                        Final_center = Final_center - L_result_C;
                        Final_left = Final_left + L_result_C;
                    } else if (c == 2){
                        Final_center = Final_center - L_result_C;
                    }
                }

                if (Int_center <= L_result_C) {
                    if (c == 1){
                        Final_left = Final_left + Int_center;
                        Final_center = Final_center - Int_center;
                        L_result_C = Int_center;
                    } else if (c == 2){
                        Final_center = Final_center - Int_center;
                        L_result_C = Int_left;
                    } else if (c == 3){
                        L_result_C = Int_left;
                    }
                }
            }

            if (L_direct_C == 2 && L_direct_R != 1){
                if (Int_left > L_result_C){
                    if (c == 1){
                        Final_left = Final_left - L_result_C;
                        Final_center = Final_center + L_result_C;
                    } else if (c == 2){
                        Final_left = Final_left - L_result_C;
                    }

                }

                if (Int_left <= L_result_C) {
                    if (c == 1){
                        Final_center = Final_center + Int_left;
                        Final_left = Final_left - Int_left;
                        L_result_C = Int_left;
                    } else if (c == 2){
                        Final_left = Final_left - Int_left;
                        L_result_C = Int_left;
                    } else if (c == 3){
                        L_result_C = Int_left;
                    }
                }
            }
        }

        if (L_direct_R != 3 && L_direct_R != 0 && Int_left != 0 && Int_right != 0){ //  Сумування між Left і Right
            if (L_direct_R == 1 && L_direct_C != 2){
                if (Int_left > L_result_R){
                    if (c == 1){
                        Final_left = Final_left - L_result_R;
                        Final_right = Final_right + L_result_R;
                    } else if (c == 2){
                        Final_left = Final_left - L_result_R;
                    }
                }

                if (Int_left <= L_result_R) {
                    if (c == 1){
                        Final_right = Final_right + Int_left;
                        Final_left = Final_left - Int_left;
                        L_result_R = Int_left;
                    } else if (c == 2){
                        Final_left = Final_left - Int_left;
                        L_result_R = Int_left;
                    } else if (c == 3){
                        L_result_R = Int_left;
                    }
                }
            }

            if (L_direct_R == 2 && C_direct_R != 1){
                if (Int_right > L_result_R){
                    if (c == 1){
                        Final_right = Final_right - L_result_R;
                        Final_left = Final_left + L_result_R;
                    } else if (c == 2){
                        Final_right = Final_right - L_result_R;
                    }
                }

                if (Int_right <= L_result_R) {
                    if (c == 1){
                        Final_left = Final_left + Int_right;
                        Final_right = Final_right - Int_right;
                        L_result_R = Int_right;
                    } else if (c == 2){
                        Final_right = Final_right - Int_right;
                        L_result_R = Int_right;
                    } else if (c == 3){
                        L_result_R = Int_right;
                    }

                }
            }
        }

        if (C_direct_R != 3 && C_direct_R != 0 && Int_center != 0 && Int_right != 0){ // Сумування між Center і Right
            if (C_direct_R == 1 && L_direct_R != 2){
                if (Int_right > C_result_R){
                    if (c == 1){
                        Final_right = Final_right - C_result_R;
                        Final_center = Final_center + C_result_R;
                    } else if (c == 2){
                        Final_right = Final_right - C_result_R;
                    }
                }

                if (Int_right <= C_result_R) {
                    if (c == 1){
                        Final_center = Final_center + Int_right;
                        Final_right = Final_right - Int_right;
                        C_result_R = Int_right;
                    } else if (c == 2){
                        Final_right = Final_right - Int_right;
                        C_result_R = Int_right;
                    } else if (c == 3){
                        C_result_R = Int_right;
                    }
                }
            }

            if (C_direct_R == 2 && L_direct_C != 1){
                if (Int_center > C_result_R){
                    if (c == 1){
                        Final_center = Final_center - C_result_R;
                        Final_right = Final_right + C_result_R;
                    } else if (c == 2){
                        Final_center = Final_center - C_result_R;
                    }
                }

                if (Int_center <= C_result_R) {
                    if (c == 1){
                        Final_right = Final_right + Int_center;
                        Final_center = Final_center - Int_center;
                        C_result_R = Int_center;
                    } else if (c == 2){
                        Final_center = Final_center - Int_center;
                        C_result_R = Int_center;
                    } else if (c == 3){
                        C_result_R = Int_center;
                    }
                }
            }
        }

        // Сумування 2 напрямків

        if (L_direct_C == 2 && L_direct_R == 1 && Int_left != 0){ // Сумування 2 напрямків на left

            if (Int_left > (L_result_C + L_result_R)){
                if (c == 1){
                    Final_right = Final_right + L_result_R;
                    Final_center = Final_center + L_result_C;
                    Final_left = Final_left - (L_result_C + L_result_R);
                } else if (c == 2){
                    Final_left = Final_left - (L_result_C + L_result_R);
                }
            }

            if (Int_left <= (L_result_C + L_result_R)){
                double percentL = (doubleR + doubleC) / doubleL;
                int roundedR, roundedC;
                roundedR = (int)Math.round(Int_right / percentL);
                roundedC = (int)Math.round(Int_center / percentL);

                if (Int_left < (roundedR + roundedC) || Int_right == Int_center){
                    int h = 0;
                    if (C_direct_R != 1 && C_direct_R != 2){
                        if (Int_center > Int_right){h = 1;}
                        if (Int_right > Int_center){h = 2;}
                        if (Int_right == Int_center){
                            Random random = new Random();
                            h = random.nextInt(2) + 1;
                        }
                    }

                    if (C_direct_R == 1 || h == 1){
                        roundedC = (int)Math.ceil(Int_center / percentL);
                        roundedR = (int)Math.floor(Int_right / percentL);
                    }
                    if (C_direct_R == 2 || h == 2){
                        roundedR = (int)Math.ceil(Int_right / percentL);
                        roundedC = (int)Math.floor(Int_center / percentL);
                    }
                }

                if (c == 1){
                    Final_right = Final_right + roundedR;
                    Final_center = Final_center + roundedC;
                    Final_left = Final_left - Int_left;
                } else if (c == 2){
                    Final_left = Final_left - Int_left;
                }

                L_result_R = roundedR;
                L_result_C = roundedC;
            }
        }

        if (C_direct_R == 2 && L_direct_C == 1 && Int_center != 0) { // Сумування 2 напрямків на center

            if (Int_center > (C_result_R + L_result_C)){
                if (c == 1){
                    Final_right = Final_right + C_result_R;
                    Final_left = Final_left + L_result_C;
                    Final_center = Final_center - (C_result_R + L_result_C);
                } else if (c == 2){
                    Final_center = Final_center - (C_result_R + L_result_C);
                }
            }

            if (Int_center <= (C_result_R + L_result_C)){
                double percentC = (doubleR + doubleL) / doubleC;
                int roundedR, roundedL;
                roundedR = (int) Math.round(Int_right / percentC);
                roundedL = (int) Math.round(Int_left / percentC);

                if (Int_center < (roundedR + roundedL) || Int_right == Int_left){
                    int h = 0;
                    if (L_direct_R != 2 && L_direct_R != 1){
                        if (Int_right > Int_left){h = 2;}
                        if (Int_left > Int_right){h = 1;}
                        if (Int_right == Int_left){
                            Random random = new Random();
                            h = random.nextInt(2) + 1;
                        }
                    }
                    if (L_direct_R == 1 || h == 1){
                        roundedR = (int)Math.ceil(Int_right / percentC);
                        roundedL = (int)Math.floor(Int_left / percentC);
                    }
                    if (L_direct_R == 2 || h == 2){
                        roundedL = (int)Math.ceil(Int_left / percentC);
                        roundedR = (int)Math.floor(Int_right / percentC);
                    }
                }

                if (c == 1){
                    Final_right = Final_right + roundedR;
                    Final_left = Final_left + roundedL;
                    Final_center = Final_center - Int_center;
                } else if (c == 2){
                    Final_center = Final_center - Int_center;
                }

                C_result_R = roundedR;
                L_result_C = roundedL;
            }
        }

        if (L_direct_R == 2 && C_direct_R == 1 && Int_right != 0){ // Сумування 2 напрямків на right

            if (Int_right > (C_result_R + L_result_R)){
                if (c == 1){
                    Final_left = Final_left + L_result_R;
                    Final_center = Final_center + C_result_R;
                    Final_right = Final_right - (C_result_R + L_result_R);
                } else if (c == 2){
                    Final_right = Final_right - (C_result_R + L_result_R);
                }
            }

            if (Int_right <= (C_result_R + L_result_R)){
                double percentR = (doubleL + doubleC) / doubleR;
                int roundedL, roundedC;
                roundedL = (int)Math.round(Int_left / percentR);
                roundedC = (int)Math.round(Int_center / percentR);

                if (Int_right < (roundedL + roundedC) || Int_left == Int_center){
                    int h = 0;
                    if (L_direct_C != 1 && L_direct_C != 2){
                        if (Int_left > Int_center){h = 1;}
                        if (Int_center > Int_left){h = 2;}
                        if (Int_left == Int_center){
                            Random random = new Random();
                            h = random.nextInt(2) + 1;
                        }
                    }
                    if (L_direct_C == 1 || h == 1){
                        roundedL = (int)Math.ceil(Int_left / percentR);
                        roundedC = (int)Math.floor(Int_center / percentR);
                    }
                    if (L_direct_C == 2 || h == 2){
                        roundedC = (int)Math.ceil(Int_center / percentR);
                        roundedL = (int)Math.floor(Int_left / percentR);
                    }
                }

                if (c == 1){
                    Final_left = Final_left + roundedL;
                    Final_center = Final_center + roundedC;
                    Final_right = Final_right - Int_right;
                } else if (c == 2){
                    Final_right = Final_right - Int_right;
                }

                L_result_R = roundedL;
                C_result_R = roundedC;
            }
        }
    }
}
