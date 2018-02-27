theory Nikolov140367900
  imports Main
          VDMToolkit
begin

text {*  Georgi Nikolov   140367900 *}


(*=========================================================================================*)
section {* Introduction *}


text {* Write here your design decisions as text.

        It is a good idea to structure your Isabelle model per VDM construct 
        in (sub)sections. We add section snipets for you below.

        Key features of Isabelle syntax needed in the definitions are given as 
        comments. 
      *}

(*=========================================================================================*)
section {* VDM values *}

text {* Write here values you might have declared in Overture. Remember that in Isabelle
        you must declare before you can use! VDM values can be given as Isabelle @{term abbreviation}.
      *}

abbreviation 
   GS :: VDMNat where "GS \<equiv> 4"
abbreviation
   MAX_BOXES :: VDMNat where "MAX_BOXES \<equiv> (GS-1) * (GS-1)"
abbreviation
  MAX_MOVES :: VDMNat where "MAX_MOVES \<equiv> 2 * GS * (GS-1)"
abbreviation
  MAX_DOTS :: VDMNat where "MAX_DOTS \<equiv> (GS*GS)-1"
  


(*=========================================================================================*)
section {* VDM types and their invariants *}

text {* VDM enumerations can be Isabelle data type. For example *}

--{* datatype name :: name = VALUE1 | VALUEN *}
datatype Player = P1 | P2

(*------------------------------------------------------------------------------------------*)
subsection {* Dot *}
type_synonym Dot = "VDMNat"

definition
  inv_Dot :: "Dot \<Rightarrow> \<bool>"
  where
    "inv_Dot d \<equiv> inv_VDMNat d \<and> d \<le> MAX_DOTS"
(*------------------------------------------------------------------------------------------*)
subsection{* Move *}
record Move =
  x_move :: Dot
  y_move :: Dot

definition
  inv_Move :: "Move  \<Rightarrow> \<bool>"
  where 
    "inv_Move m \<equiv> inv_Dot(x_move m) \<and> inv_Dot(y_move m) \<and> (x_move m) < (y_move m)"

(*------------------------------------------------------------------------------------------*)
subsection {* Moves  definition*}
  
type_synonym Moves = "Move VDMSet"

definition
  inv_Moves :: "Moves \<Rightarrow> \<bool>"
  where "inv_Moves mv \<equiv> inv_SetElems inv_Move mv \<and> int(card mv) \<le> MAX_MOVES"


(*=========================================================================================*)
section {* VDM explicitly defined functions *}


(*-----------------------------------------------------------------------------------------*)
subsection {*End game*}

definition
  end_game :: "VDMNat \<Rightarrow> VDMNat \<Rightarrow> \<bool>"
  where
    "end_game p1_boxes p2_boxes \<equiv> p1_boxes+p2_boxes = MAX_BOXES"

definition
  pre_end_game :: "VDMNat \<Rightarrow> VDMNat \<Rightarrow> \<bool>"
  where
    "pre_end_game p1_boxes p2_boxes \<equiv> inv_VDMNat p1_boxes \<and> inv_VDMNat p2_boxes"

definition
  post_end_game :: "VDMNat \<Rightarrow> VDMNat \<Rightarrow> \<bool> \<Rightarrow> \<bool>"
  where 
    "post_end_game p1_b p2_b res \<equiv> pre_end_game p1_b p2_b"

subsection {*Winning boxes count*}

definition
  win_box_c :: "VDMNat \<Rightarrow> VDMNat \<Rightarrow> VDMNat"
  where
    "win_box_c p1 p2 \<equiv> if p1>p2 then p1 else p2"

definition
  pre_win_box_c :: "VDMNat \<Rightarrow> VDMNat \<Rightarrow> \<bool>"
  where
    "pre_win_box_c p1 p2\<equiv> inv_VDMNat p1 \<and> inv_VDMNat p2 \<and> pre_end_game p1 p2 \<and> end_game p1 p2
    \<and> post_end_game p1 p2 (end_game p1 p2)"

definition
   post_win_box_c :: "VDMNat \<Rightarrow> VDMNat \<Rightarrow> VDMNat \<Rightarrow> \<bool>"
  where
    "post_win_box_c p1 p2 res \<equiv> inv_VDMNat p1 \<and> inv_VDMNat p2 \<and> inv_VDMNat res"

subsection {*Who wins*}
definition
  who_wins :: "VDMNat \<Rightarrow> VDMNat \<Rightarrow> Player"
  where
    "who_wins p1 p2 \<equiv> if p1 > p2 then P1 else P2"

definition
  pre_who_wins :: "VDMNat \<Rightarrow> VDMNat \<Rightarrow> \<bool>"
  where
    "pre_who_wins nat1 nat2 \<equiv> inv_VDMNat nat1 \<and> inv_VDMNat nat2"

definition
  post_who_wins :: "VDMNat \<Rightarrow> VDMNat \<Rightarrow> Player \<Rightarrow> \<bool>"
  where
    "post_who_wins nat1 nat2 pl \<equiv> inv_VDMNat nat1 \<and> inv_VDMNat nat2 \<and> (pl=P1 \<or> pl=P2) \<and>
    pre_end_game nat1 nat2 \<and> end_game nat1 nat2 \<and> post_end_game nat1 nat2 (end_game nat1 nat2)"

subsection {*Make move*}

definition
  make_move :: "Move \<Rightarrow> Moves \<Rightarrow> Moves"
  where
    "make_move m moves \<equiv> moves \<union> {m}"

definition
  pre_make_move :: "Move \<Rightarrow> Moves \<Rightarrow> \<bool>"
  where
    "pre_make_move m moves \<equiv> inv_Move m \<and> inv_VDMSet moves \<and> (m \<notin> moves)"

definition
  post_make_move :: "Move \<Rightarrow> Moves \<Rightarrow> Moves \<Rightarrow> \<bool>"
  where
    "post_make_move m moves res \<equiv> inv_Move m \<and> inv_VDMSet moves \<and> 
    inv_VDMSet res \<and> pre_make_move m moves \<and> (m \<in> make_move m moves)"

subsection {*Check horizontal move*}

definition
  check_horizontal_move :: "Move \<Rightarrow> \<bool>"
  where
    "check_horizontal_move m \<equiv> (y_move m) - (x_move m) = GS"

definition
  pre_check_horizontal_move :: "Move \<Rightarrow> \<bool>"
  where "pre_check_horizontal_move m \<equiv> inv_Move m"

(*No need to include the precondition in the post condition here, since it does inv_Move, which we
do anyway in the post condition ?*)
definition
  post_check_horizontal_move :: "Move \<Rightarrow> \<bool> \<Rightarrow> \<bool>"
  where "post_check_horizontal_move m res \<equiv> inv_Move m"

subsection  {*check vertical move*}

definition
  check_vertical_move :: "Move \<Rightarrow> \<bool>"
  where
    "check_vertical_move m \<equiv> (y_move m) - (x_move m) = 1"

definition
  pre_check_vertical_move :: "Move \<Rightarrow> \<bool>"
  where "pre_check_vertical_move m \<equiv> inv_Move m"

definition
  post_check_vertical_move :: "Move \<Rightarrow> \<bool> \<Rightarrow> \<bool>"
  where "post_check_vertical_move m res \<equiv> inv_Move m"

subsection {*check if move on last row*}

definition
  check_if_move_on_last_row :: "Move \<Rightarrow> \<bool>"
  where "check_if_move_on_last_row m \<equiv>
        (y_move m) mod GS = GS-1"

definition
  pre_check_if_move_on_last_row :: "Move \<Rightarrow> \<bool>"
  where "pre_check_if_move_on_last_row m \<equiv> inv_Move m"

definition
  post_check_if_move_on_last_row :: "Move \<Rightarrow> \<bool> \<Rightarrow> \<bool>"
  where "post_check_if_move_on_last_row m res \<equiv> inv_Move m"

subsection {*Swtich player*}

definition
  switch_player :: "Player \<Rightarrow> Player"
  where "switch_player p \<equiv> (if (p = P1) then P2 else P1)"

definition
  pre_switch_player :: "Player \<Rightarrow> \<bool>"
  where "pre_switch_player p \<equiv> (p = P1 \<or> p = P2)"
 
definition
  post_switch_player :: "Player \<Rightarrow> Player \<Rightarrow> \<bool>"
  where "post_switch_player p res \<equiv> p \<noteq> res"

subsection {*Check box*}

definition
  check_box :: "Move \<Rightarrow> Moves \<Rightarrow> \<bool>"
  where "check_box m moves_pl \<equiv>  card ({
      \<lparr>x_move= (x_move m), y_move= (y_move m)+GS\<rparr>,
      \<lparr>x_move= (x_move m), y_move= (y_move m)+1\<rparr>,
      \<lparr>x_move= (x_move m)+1, y_move= (y_move m)+1+GS\<rparr>,
      \<lparr>x_move= (x_move m)+GS, y_move= (y_move m)+1+GS+1\<rparr>
      } \<inter> moves_pl) = 4
      \<and> inv_Move  \<lparr>x_move= (x_move m), y_move= (y_move m)+GS\<rparr>
      \<and> inv_Move \<lparr>x_move= (x_move m), y_move= (y_move m)+1\<rparr>
      \<and> inv_Move \<lparr>x_move= (x_move m)+1, y_move= (y_move m)+1+GS\<rparr>
      \<and> inv_Move \<lparr>x_move= (x_move m)+GS, y_move= (y_move m)+1+GS+1\<rparr>"
    
definition
  pre_check_box :: "Move \<Rightarrow> Moves \<Rightarrow> \<bool>"
  where
    "pre_check_box m moves \<equiv> inv_Move m \<and> inv_Moves moves"

definition
  post_check_box :: "Move \<Rightarrow> Moves \<Rightarrow> \<bool> \<Rightarrow> \<bool>"
  where "post_check_box m moves res \<equiv> inv_Move m \<and> inv_Moves moves"

(*=========================================================================================*)
section {* VDM state *}

record DotsAndBoxesSt =
    possible_moves :: Moves
    moves_played :: Moves
    p1_boxes :: VDMNat
    p2_boxes :: VDMNat
    current_p :: Player

definition 
  inv_DotsAndBoxes_flat :: "Moves \<Rightarrow> Moves \<Rightarrow> VDMNat \<Rightarrow> VDMNat \<Rightarrow> Player \<Rightarrow> \<bool>"
  where
   "inv_DotsAndBoxes_flat p_moves played_m p1_b p2_b curr_p \<equiv> inv_Moves p_moves \<and> inv_Moves played_m 
   \<and> inv_VDMNat p1_b \<and> inv_VDMNat p2_b \<and> (p1_b+p2_b \<le> MAX_BOXES) \<and> (curr_p = P1 \<or> curr_p = P2) "

definition
  inv_DotsAndBoxes :: "DotsAndBoxesSt \<Rightarrow> \<bool>"
  where
    "inv_DotsAndBoxes st = inv_DotsAndBoxes_flat (possible_moves st) (moves_played st) (p1_boxes st)
    (p2_boxes st) (current_p st)"

definition
  init_DotsAndBoxes :: "DotsAndBoxesSt"
  where 
  "init_DotsAndBoxes \<equiv> \<lparr>possible_moves={}, moves_played={}, p1_boxes=0, p2_boxes=0, current_p=P1\<rparr>"

definition
  post_init_DotsAndBoxes :: "\<bool>"
  where
    "post_init_DotsAndBoxes \<equiv> inv_DotsAndBoxes init_DotsAndBoxes"

subsection {* State satisfiability PO*}

definition
  PO_DotsAndBoxes_initialise_sat_obl :: "\<bool>"
  where "PO_DotsAndBoxes_initialise_sat_obl \<equiv> True"


(*=========================================================================================*)
section {* VDM operations (over the state) *}

subsection{*pick move*}

definition
pre_pick_move :: "DotsAndBoxesSt \<Rightarrow> \<bool>"
where "pre_pick_move nimSt \<equiv>
inv_DotsAndBoxes nimSt \<and> (MAX_MOVES - vdm_card (possible_moves nimSt) > 0 )"

definition
post_pick_move :: "Move \<Rightarrow> DotsAndBoxesSt \<Rightarrow> DotsAndBoxesSt \<Rightarrow> \<bool>"
where "post_pick_move res bst ast \<equiv> inv_Move res \<and> inv_DotsAndBoxes bst \<and> 
      inv_DotsAndBoxes ast \<and> res \<in> (moves_played ast) \<and> res \<notin> (moves_played bst)"

subsection  {*add box*}

definition 
pre_add_box :: " DotsAndBoxesSt \<Rightarrow> \<bool>"
where "pre_add_box  bst \<equiv> inv_DotsAndBoxes bst
       \<and> ((p1_boxes bst)+(p2_boxes bst) < MAX_BOXES)"

definition 
post_add_box :: "\<bool> \<Rightarrow> DotsAndBoxesSt \<Rightarrow> DotsAndBoxesSt \<Rightarrow> \<bool>"
where "post_add_box res bst ast \<equiv>  inv_DotsAndBoxes bst \<and> inv_DotsAndBoxes ast
      \<and> (((p1_boxes ast) > (p1_boxes bst)) \<or> ((p2_boxes ast) > (p2_boxes bst))) \<and> 
      ((p1_boxes ast) + (p2_boxes ast)\<le> MAX_BOXES) "

subsection {* make play *}

definition
pre_make_play :: "Move \<Rightarrow> DotsAndBoxesSt \<Rightarrow> \<bool>"
where "pre_make_play mv bst \<equiv> inv_Move mv \<and> inv_DotsAndBoxes bst \<and> 
      (MAX_MOVES - vdm_card (possible_moves bst) > 0) \<and> pre_make_move mv (moves_played bst)
      \<and> pre_check_horizontal_move mv \<and> pre_add_box bst \<and> pre_check_vertical_move mv"

definition 
post_make_play ::  "Move \<Rightarrow> DotsAndBoxesSt \<Rightarrow> DotsAndBoxesSt \<Rightarrow> \<bool>"
where "post_make_play res bst ast \<equiv> inv_Move res \<and> inv_DotsAndBoxes ast \<and> inv_DotsAndBoxes bst \<and>
       res \<in> (moves_played ast) \<and> post_make_move res (moves_played bst) (moves_played ast) \<and>
       post_add_box (pre_add_box bst) bst ast \<and> 
       post_check_vertical_move res (pre_check_vertical_move res) \<and>
       post_check_horizontal_move res (pre_check_horizontal_move res) \<and>
       (((p1_boxes bst) = (p1_boxes ast) \<or>
       (p2_boxes bst) = (p2_boxes ast)) \<longrightarrow> post_switch_player (current_p bst) (current_p ast))
       "

section {*play_game*}

definition
pre_play_game_dotsAndBoxes :: "DotsAndBoxesSt \<Rightarrow> \<bool>"
where "pre_play_game_dotsAndBoxes bst \<equiv> inv_DotsAndBoxes bst \<and>
      vdm_card (possible_moves bst) = MAX_MOVES \<and>
      ((p1_boxes bst)+(p2_boxes bst) =0)\<and>
      vdm_card (moves_played bst) = 0"

definition
post_play_game_dotsAndBoxes :: "DotsAndBoxesSt \<Rightarrow> DotsAndBoxesSt \<Rightarrow> \<bool>"
where "post_play_game_dotsAndBoxes bst ast \<equiv>
      inv_DotsAndBoxes bst \<and> inv_DotsAndBoxes ast \<and> 
      (vdm_card (possible_moves bst) = MAX_MOVES) \<and>
      (vdm_card (moves_played ast) = MAX_MOVES)\<and>
      ((possible_moves ast) = {})\<and>
      (vdm_card (moves_played ast) = MAX_MOVES) \<and> 
      ((p1_boxes ast) + (p2_boxes ast) \<le> MAX_BOXES)"



(*=========================================================================================*)
section {* VDM proof obligations *}

text {* Overture returned about 58 POs, most of them were trivial, and since I have not, 
      translated generate all moves function as it's not needed for the proof, I didn't want
      build executable model
      *}
subsection {*State satisfiability PO*}

subsection {*State  PO*}
(*Here I think we need to check if any given DnB init, satisfies the post condition for DnB init*)
(*(exists possible_moves:Nikolov140367900`Moves, moves:Nikolov140367900`Moves,
 p1_boxes:nat, p2_boxes:nat, curr_player:Nikolov140367900`Player
 & ((p1_boxes + p2_boxes) <= MAX_BOXES))
Not sure if this is even close to correct solution.
They way I wrote it in such a way is because if we make call to post_init_dots_and boxes I think,
this will initialise the values set there, if we skip it, we can essentially just check the
state invariant and the PO defined up in the file*)
theorem DnB_state : " \<forall> st . \<exists> st . inv_DotsAndBoxes st  \<longrightarrow> PO_DotsAndBoxes_initialise_sat_obl \<longrightarrow>
                     (p1_boxes st)+(p2_boxes st) \<le> MAX_BOXES"
  unfolding inv_DotsAndBoxes_def
  apply (simp)
  unfolding PO_DotsAndBoxes_initialise_sat_obl_def
  apply (auto)
  by (metis DotsAndBoxesSt.select_convs(3) DotsAndBoxesSt.select_convs(4) add.left_neutral zero_le_numeral)
 
  
subsection {*Switch Player PO*}
theorem switch_player_po:
  "\<forall> p . pre_switch_player p \<longrightarrow> post_switch_player p (switch_player p)"
  unfolding post_switch_player_def
  apply (safe)
  by (metis Player.distinct(1) switch_player_def)
 

subsection {*check_box_po*}
(*(forall m:Nikolov140367900`Move, moves_played:Nikolov140367900`Moves &
 let m1:Nikolov140367900`Move = mk_Move((m.x), ((m.x) + GS)) in
 let m2:Nikolov140367900`Move = mk_Move((m.x), ((m.x) + 1)) 
in let m3:Nikolov140367900`Move = mk_Move(((m.x) + 1), 
(((m.x) + GS) + 1)) in ((inv_Move(mk_Move(((m.x) + GS), (((m.x) + GS) + 1)))
 and inv_Dot(((m.x) + GS))) and inv_Dot((((m.x) + GS) + 1))))*)

theorem "\<forall> p s . pre_check_box p s \<longrightarrow> post_check_box p s (check_box p s)"
  unfolding check_box_def
  apply (simp)
  by (simp add: post_check_box_def pre_check_box_def)

subsection {*Add box*}
(*(true => (((p2_boxes + p1_boxes) <= MAX_BOXES) => 
let mk_DotsAndBoxes(possible_moves, moves, p1_boxes, p2_boxes, first) 
= DotsAndBoxes in ((p1_boxes + p2_boxes) <= MAX_BOXES))) *)
(*Couldnt prove this one it just makes no sense to me at all, I guess i needed to
define a lemma to split the complex comprehension*)
theorem "true \<Longrightarrow> \<forall> pm mvs p1_b p2_b pl . 
        \<exists> pmv mpl p1_b p2_b pl . inv_DotsAndBoxes_flat pmv mpl p1_b p2_b pl \<longrightarrow> p1_b+p2_b\<le>MAX_BOXES
        \<longrightarrow>post_init_DotsAndBoxes"
  unfolding post_init_DotsAndBoxes_def
  apply (simp)
  unfolding inv_DotsAndBoxes_flat_def
  apply (auto)
  by smt
(***
proof -
assume a1: "\<forall>p1_b. inv_VDMNat p1_b \<and> (\<forall>p2_b. inv_VDMNat p2_b \<and> p1_b + p2_b \<le> 9
             \<and> (\<forall>curr_p. curr_p = P1 \<or> curr_p = P2))"
have f2: "\<forall>x0. (x0::\<int>) + 0 = x0"
by auto
have "\<not> (10::\<int>) \<le> 9"
by auto
then show "inv_DotsAndBoxes init_DotsAndBoxes"
using f2 a1 by metis
qed
**)
  
subsection {*Pick a move*}
(*true\<Rightarrow>let mk_DotsAndBoxes(possible_moves, moves, p1_boxes, p2_boxes, first)
 = DotsAndBoxes in ((p1_boxes + p2_boxes) <= MAX_BOXES))*)

theorem "\<forall> mvs . inv_VDMSet mvs \<longrightarrow> (MAX_BOXES - vdm_card(mvs)>0) \<Longrightarrow>
         \<exists> st . inv_DotsAndBoxes st  \<longrightarrow> PO_DotsAndBoxes_initialise_sat_obl \<longrightarrow>
         ((p1_boxes st)+(p2_boxes st) \<le> MAX_BOXES)"
  apply (auto)
  by (metis DotsAndBoxesSt.select_convs(3) DotsAndBoxesSt.select_convs(4) add.left_neutral zero_le_numeral)

subsection {*play game*}
(*Oveture produced 3 simple POs for this one so I just translated them as one here, first one was*)
(*...
((MAX_MOVES - (card moves)) > 0)
(true => pre_who_wins(p1_boxes, p2_boxes))
(true \<Rightarrow>pre_win_box_c(p1_boxes, p2_boxes))*)
lemma "\<forall> mvs . inv_VDMSet mvs \<longrightarrow> (MAX_BOXES - vdm_card(mvs)\<le>0) \<Longrightarrow> 
      \<exists> p1_b p2_b pl . pre_who_wins p1_b p2_b \<longrightarrow> post_who_wins p1_b p2_b pl"
  unfolding pre_who_wins_def
  apply(simp)
  unfolding post_who_wins_def
  apply(auto)
  by (meson diff_ge_0_iff_ge inv_VDMNat_def not_numeral_le_neg_numeral)
 
(*-----------------------------------------------------------------------------------------*)
subsection {* Well formedness (if any) *}

--{* lemma goal_name : "predicate" proof_script *}
lemma myFun_subtype: "P = NP" 
oops

(*-----------------------------------------------------------------------------------------*)
subsection {* Satisfiability (of each VDM operation) *}

lemma myFun_satisfiability: "P = NP"
oops

(*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*)
subsubsection {* Operation1 .. N *}

lemma "P = NP"
oops

(*-----------------------------------------------------------------------------------------*)
subsection {* Sanity checks *}


lemma "P = NP"
oops

(*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*)
subsubsection {* Single winner or draw *}

lemma "P = NP"
oops

(*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*)
subsubsection {* Moves within board maximum size *}

lemma "P = NP"
oops

(*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*)
subsubsection {* Moves fairness (i.e. one player move per round) *}

lemma "P = NP"
oops

end