package com.eclipse.panel.gameObject;

import com.eclipse.panel.dbConnect.DBLoadObject;

import java.util.HashMap;
import java.util.Map;

public class ROMap {

    // Map DB
    public static final String TABLE_NAME = "maps";
    public static final String TABLE_KEY = "id";
    public static final Map<String, Integer[]> mapsSize = new HashMap<>();
    static {
        mapsSize.put("n_castle", new Integer[] {200, 200});
        mapsSize.put("gefenia04", new Integer[] {300, 300});
        mapsSize.put("4@tower", new Integer[] {400, 400});
        mapsSize.put("tha_t09", new Integer[] {180, 180});
        mapsSize.put("ama_fild01", new Integer[] {400, 400});
        mapsSize.put("auction_02", new Integer[] {200, 100});
        mapsSize.put("bat_b01", new Integer[] {400, 300});
        mapsSize.put("manuk", new Integer[] {400, 400});
        mapsSize.put("ein_fild03", new Integer[] {400, 400});
        mapsSize.put("ama_dun01", new Integer[] {240, 240});
        mapsSize.put("moc_fild22", new Integer[] {400, 400});
        mapsSize.put("payon", new Integer[] {300, 360});
        mapsSize.put("pvp_room", new Integer[] {100, 100});
        mapsSize.put("job_star", new Integer[] {200, 184});
        mapsSize.put("xmas", new Integer[] {300, 360});
        mapsSize.put("iz_dun01", new Integer[] {300, 300});
        mapsSize.put("yuno_in02", new Integer[] {200, 240});
        mapsSize.put("gef_dun01", new Integer[] {300, 300});
        mapsSize.put("niflheim", new Integer[] {400, 300});
        mapsSize.put("gef_dun00", new Integer[] {200, 200});
        mapsSize.put("yuno_in03", new Integer[] {260, 216});
        mapsSize.put("kh_kiehl01", new Integer[] {200, 200});
        mapsSize.put("iz_dun00", new Integer[] {400, 400});
        mapsSize.put("gld_dun01", new Integer[] {240, 240});
        mapsSize.put("ra_temple", new Integer[] {240, 240});
        mapsSize.put("gefg_cas01", new Integer[] {220, 220});
        mapsSize.put("thor_v01", new Integer[] {300, 300});
        mapsSize.put("nif_in", new Integer[] {200, 200});
        mapsSize.put("ein_fild02", new Integer[] {400, 400});
        mapsSize.put("tha_t08", new Integer[] {140, 200});
        mapsSize.put("jupe_ele", new Integer[] {100, 124});
        mapsSize.put("auction_01", new Integer[] {200, 100});
        mapsSize.put("gl_sew04", new Integer[] {300, 300});
        mapsSize.put("moc_fild09", new Integer[] {400, 400});
        mapsSize.put("moc_fild21", new Integer[] {400, 400});
        mapsSize.put("ama_dun02", new Integer[] {240, 240});
        mapsSize.put("cmd_fild08", new Integer[] {400, 400});
        mapsSize.put("thor_v03", new Integer[] {300, 300});
        mapsSize.put("gefg_cas03", new Integer[] {280, 300});
        mapsSize.put("gld_dun03", new Integer[] {300, 300});
        mapsSize.put("iz_dun02", new Integer[] {400, 400});
        mapsSize.put("gl_step", new Integer[] {240, 240});
        mapsSize.put("yuno_in01", new Integer[] {200, 200});
        mapsSize.put("gef_dun02", new Integer[] {260, 260});
        mapsSize.put("que_ba", new Integer[] {300, 300});
        mapsSize.put("jupe_core", new Integer[] {300, 300});
        mapsSize.put("guild_vs4", new Integer[] {100, 100});
        mapsSize.put("guild_vs5", new Integer[] {100, 100});
        mapsSize.put("job_cru", new Integer[] {200, 200});
        mapsSize.put("sch_gld", new Integer[] {400, 400});
        mapsSize.put("xmas_fild01", new Integer[] {280, 280});
        mapsSize.put("1@cata", new Integer[] {300, 240});
        mapsSize.put("gef_dun03", new Integer[] {240, 240});
        mapsSize.put("gld_dun02", new Integer[] {200, 200});
        mapsSize.put("kh_kiehl02", new Integer[] {100, 100});
        mapsSize.put("iz_dun03", new Integer[] {300, 300});
        mapsSize.put("moc_castle", new Integer[] {200, 200});
        mapsSize.put("gefg_cas02", new Integer[] {200, 220});
        mapsSize.put("thor_v02", new Integer[] {240, 240});
        mapsSize.put("gef_tower", new Integer[] {200, 200});
        mapsSize.put("cmd_fild09", new Integer[] {400, 400});
        mapsSize.put("ein_fild01", new Integer[] {400, 400});
        mapsSize.put("moc_fild20", new Integer[] {400, 400});
        mapsSize.put("ama_dun03", new Integer[] {240, 240});
        mapsSize.put("moc_fild08", new Integer[] {400, 400});
        mapsSize.put("thor_camp", new Integer[] {300, 360});
        mapsSize.put("lighthalzen", new Integer[] {400, 360});
        mapsSize.put("gefenia02", new Integer[] {300, 300});
        mapsSize.put("treasure01", new Integer[] {200, 200});
        mapsSize.put("gl_sew01", new Integer[] {280, 280});
        mapsSize.put("prt_maze02", new Integer[] {200, 200});
        mapsSize.put("ein_fild05", new Integer[] {400, 400});
        mapsSize.put("mosk_dun03", new Integer[] {300, 300});
        mapsSize.put("moc_fild18", new Integer[] {400, 400});
        mapsSize.put("xmas_dun02", new Integer[] {260, 260});
        mapsSize.put("yuno_in04", new Integer[] {200, 140});
        mapsSize.put("ayo_fild01", new Integer[] {360, 360});
        mapsSize.put("guild_vs1", new Integer[] {100, 100});
        mapsSize.put("que_ng", new Integer[] {200, 200});
        mapsSize.put("e_tower", new Integer[] {300, 300});
        mapsSize.put("lhz_cube", new Integer[] {260, 212});
        mapsSize.put("kh_dun01", new Integer[] {240, 240});
        mapsSize.put("gl_dun02", new Integer[] {300, 300});
        mapsSize.put("man_fild01", new Integer[] {400, 400});
        mapsSize.put("yuno_in05", new Integer[] {200, 200});
        mapsSize.put("2@tower", new Integer[] {400, 400});
        mapsSize.put("sec_in02", new Integer[] {200, 200});
        mapsSize.put("pvp_2vs2", new Integer[] {80, 80});
        mapsSize.put("1@orcs", new Integer[] {200, 200});
        mapsSize.put("arug_dun01", new Integer[] {400, 400});
        mapsSize.put("moc_fild19", new Integer[] {200, 200});
        mapsSize.put("mosk_dun02", new Integer[] {300, 300});
        mapsSize.put("ein_fild04", new Integer[] {400, 400});
        mapsSize.put("ein_fild10", new Integer[] {400, 400});
        mapsSize.put("prt_maze03", new Integer[] {200, 200});
        mapsSize.put("poring_w02", new Integer[] {200, 240});
        mapsSize.put("gefenia03", new Integer[] {300, 300});
        mapsSize.put("que_dan01", new Integer[] {80, 80});
        mapsSize.put("izlu2dun", new Integer[] {216, 232});
        mapsSize.put("turbo_room", new Integer[] {200, 200});
        mapsSize.put("gefenia01", new Integer[] {300, 300});
        mapsSize.put("in_moc_16", new Integer[] {200, 200});
        mapsSize.put("treasure02", new Integer[] {200, 200});
        mapsSize.put("gl_sew02", new Integer[] {316, 316});
        mapsSize.put("yuno_fild09", new Integer[] {400, 400});
        mapsSize.put("ein_fild06", new Integer[] {400, 400});
        mapsSize.put("prt_maze01", new Integer[] {200, 200});
        mapsSize.put("gef_fild09", new Integer[] {400, 400});
        mapsSize.put("xmas_dun01", new Integer[] {260, 260});
        mapsSize.put("xmas_du01a", new Integer[] {260, 260});
        mapsSize.put("gefg_cas05", new Integer[] {220, 220});
        mapsSize.put("gl_church", new Integer[] {312, 312});
        mapsSize.put("iz_dun04", new Integer[] {280, 300});
        mapsSize.put("ra_fild08", new Integer[] {400, 400});
        mapsSize.put("ein_in01", new Integer[] {300, 300});
        mapsSize.put("ra_temin", new Integer[] {340, 340});
        mapsSize.put("que_bingo", new Integer[] {100, 200});
        mapsSize.put("man_fild03", new Integer[] {400, 400});
        mapsSize.put("guild_vs2", new Integer[] {100, 100});
        mapsSize.put("ayo_fild02", new Integer[] {360, 300});
        mapsSize.put("morocc", new Integer[] {320, 320});
        mapsSize.put("kh_dun02", new Integer[] {240, 240});
        mapsSize.put("guild_vs3", new Integer[] {100, 100});
        mapsSize.put("gl_dun01", new Integer[] {300, 300});
        mapsSize.put("man_fild02", new Integer[] {400, 400});
        mapsSize.put("prt_gld", new Integer[] {320, 320});
        mapsSize.put("beach_dun", new Integer[] {300, 300});
        mapsSize.put("sec_in01", new Integer[] {200, 200});
        mapsSize.put("gld_dun04", new Integer[] {300, 300});
        mapsSize.put("ra_fild09", new Integer[] {400, 400});
        mapsSize.put("gefg_cas04", new Integer[] {200, 248});
        mapsSize.put("gon_test", new Integer[] {100, 120});
        mapsSize.put("pay_arche", new Integer[] {200, 200});
        mapsSize.put("gef_fild08", new Integer[] {380, 380});
        mapsSize.put("mosk_dun01", new Integer[] {300, 300});
        mapsSize.put("ein_fild07", new Integer[] {400, 400});
        mapsSize.put("yuno_fild08", new Integer[] {400, 400});
        mapsSize.put("gl_sew03", new Integer[] {340, 300});
        mapsSize.put("poring_w01", new Integer[] {200, 200});
        mapsSize.put("mid_campin", new Integer[] {400, 200});
        mapsSize.put("que_dan02", new Integer[] {120, 60});
        mapsSize.put("pay_dun01", new Integer[] {300, 300});
        mapsSize.put("moc_pryd03", new Integer[] {200, 200});
        mapsSize.put("nameless_in", new Integer[] {200, 200});
        mapsSize.put("new_zone03", new Integer[] {200, 200});
        mapsSize.put("pay_fild02", new Integer[] {300, 404});
        mapsSize.put("5@tower", new Integer[] {160, 160});
        mapsSize.put("payon_in01", new Integer[] {200, 200});
        mapsSize.put("alde_dun01", new Integer[] {320, 320});
        mapsSize.put("prt_fild06", new Integer[] {380, 340});
        mapsSize.put("schg_dun01", new Integer[] {400, 400});
        mapsSize.put("prt_fild07", new Integer[] {400, 400});
        mapsSize.put("ayo_in01", new Integer[] {200, 200});
        mapsSize.put("cmd_in02", new Integer[] {240, 240});
        mapsSize.put("bat_a01", new Integer[] {400, 400});
        mapsSize.put("alde_tt02", new Integer[] {400, 400});
        mapsSize.put("mjolnir_04_1", new Integer[] {400, 400});
        mapsSize.put("pay_fild03", new Integer[] {416, 300});
        mapsSize.put("amatsu", new Integer[] {316, 316});
        mapsSize.put("alberta", new Integer[] {280, 280});
        mapsSize.put("gl_prison1", new Integer[] {200, 200});
        mapsSize.put("new_zone02", new Integer[] {200, 200});
        mapsSize.put("mjolnir_01", new Integer[] {400, 400});
        mapsSize.put("moc_pryd02", new Integer[] {200, 200});
        mapsSize.put("geffen_in", new Integer[] {200, 200});
        mapsSize.put("pay_dun00", new Integer[] {200, 200});
        mapsSize.put("aldeg_cas05", new Integer[] {240, 240});
        mapsSize.put("pay_dun02", new Integer[] {300, 300});
        mapsSize.put("in_orcs01", new Integer[] {200, 200});
        mapsSize.put("mjolnir_03", new Integer[] {400, 400});
        mapsSize.put("pay_fild01", new Integer[] {400, 400});
        mapsSize.put("bat_c01", new Integer[] {200, 180});
        mapsSize.put("payon_in02", new Integer[] {100, 100});
        mapsSize.put("alde_dun02", new Integer[] {300, 300});
        mapsSize.put("geffen", new Integer[] {240, 240});
        mapsSize.put("prt_fild05", new Integer[] {400, 400});
        mapsSize.put("prt_fild11", new Integer[] {380, 320});
        mapsSize.put("que_hugel", new Integer[] {200, 200});
        mapsSize.put("ayo_in02", new Integer[] {200, 200});
        mapsSize.put("prt_fild10", new Integer[] {360, 320});
        mapsSize.put("prt_fild04", new Integer[] {400, 400});
        mapsSize.put("cmd_in01", new Integer[] {200, 200});
        mapsSize.put("alde_dun03", new Integer[] {300, 300});
        mapsSize.put("jawaii", new Integer[] {340, 360});
        mapsSize.put("payon_in03", new Integer[] {200, 200});
        mapsSize.put("job_duncer", new Integer[] {140, 172});
        mapsSize.put("prontera", new Integer[] {312, 392});
        mapsSize.put("new_zone01", new Integer[] {200, 200});
        mapsSize.put("job_monk", new Integer[] {400, 300});
        mapsSize.put("moc_pryd01", new Integer[] {200, 200});
        mapsSize.put("mjolnir_02", new Integer[] {400, 400});
        mapsSize.put("pay_dun03", new Integer[] {300, 300});
        mapsSize.put("aldeg_cas04", new Integer[] {240, 240});
        mapsSize.put("mjolnir_06", new Integer[] {400, 400});
        mapsSize.put("mjolnir_12", new Integer[] {400, 400});
        mapsSize.put("moc_pryd05", new Integer[] {232, 232});
        mapsSize.put("lhz_dun03", new Integer[] {280, 280});
        mapsSize.put("valkyrie", new Integer[] {100, 100});
        mapsSize.put("pay_fild04", new Integer[] {400, 400});
        mapsSize.put("pay_fild10", new Integer[] {400, 400});
        mapsSize.put("lou_in02", new Integer[] {300, 200});
        mapsSize.put("prt_are_in", new Integer[] {200, 200});
        mapsSize.put("kh_mansion", new Integer[] {100, 100});
        mapsSize.put("beach_dun3", new Integer[] {300, 300});
        mapsSize.put("lhz_fild02", new Integer[] {400, 400});
        mapsSize.put("gl_knt02", new Integer[] {300, 300});
        mapsSize.put("hu_in01", new Integer[] {400, 400});
        mapsSize.put("prt_fild00", new Integer[] {400, 400});
        mapsSize.put("prt_fild01", new Integer[] {400, 400});
        mapsSize.put("jupe_cave", new Integer[] {232, 100});
        mapsSize.put("lhz_fild03", new Integer[] {400, 400});
        mapsSize.put("abbey01", new Integer[] {400, 400});
        mapsSize.put("beach_dun2", new Integer[] {300, 300});
        mapsSize.put("yggdrasil01", new Integer[] {300, 300});
        mapsSize.put("que_job01", new Integer[] {160, 100});
        mapsSize.put("pay_fild11", new Integer[] {320, 360});
        mapsSize.put("pay_fild05", new Integer[] {300, 400});
        mapsSize.put("job_knt", new Integer[] {200, 200});
        mapsSize.put("new_zone04", new Integer[] {200, 200});
        mapsSize.put("alde_alche", new Integer[] {200, 200});
        mapsSize.put("nif_fild01", new Integer[] {400, 400});
        mapsSize.put("3@tower", new Integer[] {400, 400});
        mapsSize.put("gon_in", new Integer[] {200, 120});
        mapsSize.put("lhz_dun02", new Integer[] {300, 300});
        mapsSize.put("thana_boss", new Integer[] {280, 280});
        mapsSize.put("aldeg_cas01", new Integer[] {240, 264});
        mapsSize.put("mosk_que", new Integer[] {100, 240});
        mapsSize.put("moc_pryd04", new Integer[] {200, 200});
        mapsSize.put("mjolnir_07", new Integer[] {400, 400});
        mapsSize.put("tha_scene01", new Integer[] {400, 400});
        mapsSize.put("mjolnir_11", new Integer[] {400, 400});
        mapsSize.put("moc_pryd06", new Integer[] {204, 204});
        mapsSize.put("mjolnir_05", new Integer[] {400, 400});
        mapsSize.put("pay_dun04", new Integer[] {240, 240});
        mapsSize.put("aldeg_cas03", new Integer[] {240, 300});
        mapsSize.put("umbala", new Integer[] {280, 344});
        mapsSize.put("pay_fild07", new Integer[] {400, 400});
        mapsSize.put("lou_in01", new Integer[] {200, 200});
        mapsSize.put("moc_ruins", new Integer[] {200, 200});
        mapsSize.put("moc_fild22b", new Integer[] {400, 400});
        mapsSize.put("alde_dun04", new Integer[] {300, 300});
        mapsSize.put("lhz_fild01", new Integer[] {400, 400});
        mapsSize.put("glast_01", new Integer[] {400, 400});
        mapsSize.put("abbey03", new Integer[] {240, 240});
        mapsSize.put("gl_knt01", new Integer[] {300, 300});
        mapsSize.put("einbroch", new Integer[] {340, 380});
        mapsSize.put("prt_fild03", new Integer[] {400, 300});
        mapsSize.put("prt_fild02", new Integer[] {400, 400});
        mapsSize.put("sec_pri", new Integer[] {100, 100});
        mapsSize.put("abbey02", new Integer[] {300, 300});
        mapsSize.put("que_job02", new Integer[] {200, 200});
        mapsSize.put("pay_fild06", new Integer[] {400, 400});
        mapsSize.put("thana_step", new Integer[] {240, 400});
        mapsSize.put("nif_fild02", new Integer[] {400, 400});
        mapsSize.put("lhz_dun01", new Integer[] {300, 300});
        mapsSize.put("aldeg_cas02", new Integer[] {240, 240});
        mapsSize.put("mjolnir_04", new Integer[] {400, 400});
        mapsSize.put("mjolnir_10", new Integer[] {400, 400});
        mapsSize.put("mjolnir_09", new Integer[] {400, 400});
        mapsSize.put("mosk_in", new Integer[] {300, 300});
        mapsSize.put("tur_dun02", new Integer[] {300, 300});
        mapsSize.put("ra_san01", new Integer[] {280, 280});
        mapsSize.put("arug_cas03", new Integer[] {400, 400});
        mapsSize.put("que_god02", new Integer[] {200, 140});
        mapsSize.put("hu_fild06", new Integer[] {400, 400});
        mapsSize.put("force_map1", new Integer[] {200, 200});
        mapsSize.put("in_hunter", new Integer[] {200, 200});
        mapsSize.put("lou_dun03", new Integer[] {300, 300});
        mapsSize.put("prtg_cas03", new Integer[] {220, 240});
        mapsSize.put("um_fild04", new Integer[] {400, 400});
        mapsSize.put("mjo_dun01", new Integer[] {280, 340});
        mapsSize.put("ein_dun01", new Integer[] {300, 300});
        mapsSize.put("gon_dun03", new Integer[] {240, 300});
        mapsSize.put("gon_dun02", new Integer[] {300, 300});
        mapsSize.put("anthell01", new Integer[] {300, 300});
        mapsSize.put("6@tower", new Integer[] {200, 200});
        mapsSize.put("prtg_cas02", new Integer[] {224, 240});
        mapsSize.put("poring_c01", new Integer[] {200, 200});
        mapsSize.put("job_thief1", new Integer[] {360, 360});
        mapsSize.put("lou_dun02", new Integer[] {300, 300});
        mapsSize.put("gl_cas01", new Integer[] {400, 400});
        mapsSize.put("hu_fild07", new Integer[] {400, 400});
        mapsSize.put("aldeba_in", new Integer[] {260, 260});
        mapsSize.put("quiz_02", new Integer[] {400, 400});
        mapsSize.put("arug_cas02", new Integer[] {400, 400});
        mapsSize.put("ordeal_a00", new Integer[] {300, 300});
        mapsSize.put("tur_dun03", new Integer[] {248, 248});
        mapsSize.put("mjolnir_08", new Integer[] {400, 400});
        mapsSize.put("ra_san02", new Integer[] {300, 300});
        mapsSize.put("tur_dun01", new Integer[] {300, 300});
        mapsSize.put("ordeal_a02", new Integer[] {308, 308});
        mapsSize.put("airplane", new Integer[] {300, 300});
        mapsSize.put("morocc_in", new Integer[] {200, 200});
        mapsSize.put("quiz_00", new Integer[] {160, 160});
        mapsSize.put("que_god01", new Integer[] {300, 140});
        mapsSize.put("hu_fild05", new Integer[] {400, 400});
        mapsSize.put("payg_cas04", new Integer[] {300, 300});
        mapsSize.put("force_map2", new Integer[] {200, 200});
        mapsSize.put("nameless_n", new Integer[] {340, 340});
        mapsSize.put("pay_fild08", new Integer[] {280, 400});
        mapsSize.put("cave", new Integer[] {200, 200});
        mapsSize.put("alb2trea", new Integer[] {140, 140});
        mapsSize.put("gl_chyard", new Integer[] {300, 300});
        mapsSize.put("um_in", new Integer[] {200, 140});
        mapsSize.put("p_track01", new Integer[] {100, 80});
        mapsSize.put("ein_dun02", new Integer[] {300, 300});
        mapsSize.put("mjo_dun02", new Integer[] {400, 400});
        mapsSize.put("que_qsch01", new Integer[] {400, 400});
        mapsSize.put("airport", new Integer[] {300, 120});
        mapsSize.put("gon_dun01", new Integer[] {300, 300});
        mapsSize.put("mjo_dun03", new Integer[] {340, 280});
        mapsSize.put("prtg_cas01", new Integer[] {220, 220});
        mapsSize.put("anthell02", new Integer[] {300, 300});
        mapsSize.put("poring_c02", new Integer[] {240, 240});
        mapsSize.put("2@orcs", new Integer[] {200, 200});
        mapsSize.put("lou_dun01", new Integer[] {300, 300});
        mapsSize.put("gl_cas02", new Integer[] {200, 200});
        mapsSize.put("pay_fild09", new Integer[] {400, 400});
        mapsSize.put("force_map3", new Integer[] {200, 200});
        mapsSize.put("payg_cas05", new Integer[] {300, 300});
        mapsSize.put("hu_fild04", new Integer[] {400, 400});
        mapsSize.put("alberta_in", new Integer[] {200, 200});
        mapsSize.put("job_wiz", new Integer[] {200, 200});
        mapsSize.put("quiz_01", new Integer[] {400, 400});
        mapsSize.put("arug_cas01", new Integer[] {400, 400});
        mapsSize.put("ra_san03", new Integer[] {300, 300});
        mapsSize.put("tur_dun04", new Integer[] {200, 200});
        mapsSize.put("veins", new Integer[] {400, 400});
        mapsSize.put("moscovia", new Integer[] {400, 400});
        mapsSize.put("que_thor", new Integer[] {200, 100});
        mapsSize.put("comodo", new Integer[] {360, 380});
        mapsSize.put("prt_monk", new Integer[] {400, 300});
        mapsSize.put("2@nyd", new Integer[] {400, 400});
        mapsSize.put("aru_gld", new Integer[] {400, 400});
        mapsSize.put("payg_cas01", new Integer[] {300, 160});
        mapsSize.put("monk_in", new Integer[] {200, 200});
        mapsSize.put("mosk_fild01", new Integer[] {200, 200});
        mapsSize.put("odin_tem01", new Integer[] {400, 400});
        mapsSize.put("gl_in01", new Integer[] {200, 200});
        mapsSize.put("arug_que01", new Integer[] {200, 200});
        mapsSize.put("prtg_cas05", new Integer[] {300, 300});
        mapsSize.put("spl_fild02", new Integer[] {400, 400});
        mapsSize.put("jawaii_in", new Integer[] {200, 140});
        mapsSize.put("prt_fild09", new Integer[] {400, 400});
        mapsSize.put("um_fild02", new Integer[] {400, 400});
        mapsSize.put("um_fild03", new Integer[] {400, 400});
        mapsSize.put("kh_rossi", new Integer[] {300, 300});
        mapsSize.put("mosk_ship", new Integer[] {200, 200});
        mapsSize.put("prt_fild08", new Integer[] {400, 400});
        mapsSize.put("spl_fild03", new Integer[] {400, 400});
        mapsSize.put("prtg_cas04", new Integer[] {300, 300});
        mapsSize.put("ve_in", new Integer[] {400, 400});
        mapsSize.put("ama_test", new Integer[] {100, 132});
        mapsSize.put("ayo_dun02", new Integer[] {300, 300});
        mapsSize.put("pay_gld", new Integer[] {400, 400});
        mapsSize.put("2@cata", new Integer[] {160, 160});
        mapsSize.put("izlude", new Integer[] {268, 268});
        mapsSize.put("hu_fild01", new Integer[] {400, 400});
        mapsSize.put("ayothaya", new Integer[] {320, 320});
        mapsSize.put("mag_dun02", new Integer[] {260, 260});
        mapsSize.put("prt_castle", new Integer[] {400, 400});
        mapsSize.put("xmas_in", new Integer[] {200, 200});
        mapsSize.put("tur_dun05", new Integer[] {100, 100});
        mapsSize.put("ra_san04", new Integer[] {240, 240});
        mapsSize.put("gon_fild01", new Integer[] {400, 400});
        mapsSize.put("payg_cas02", new Integer[] {300, 300});
        mapsSize.put("hu_fild03", new Integer[] {400, 400});
        mapsSize.put("mosk_fild02", new Integer[] {300, 300});
        mapsSize.put("odin_tem02", new Integer[] {400, 400});
        mapsSize.put("job_sage", new Integer[] {200, 200});
        mapsSize.put("arena_room", new Integer[] {200, 200});
        mapsSize.put("aldebaran", new Integer[] {280, 280});
        mapsSize.put("spl_fild01", new Integer[] {400, 400});
        mapsSize.put("um_fild01", new Integer[] {400, 400});
        mapsSize.put("ayo_dun01", new Integer[] {300, 300});
        mapsSize.put("rachel", new Integer[] {300, 300});
        mapsSize.put("odin_tem03", new Integer[] {400, 400});
        mapsSize.put("mid_camp", new Integer[] {400, 400});
        mapsSize.put("ve_in02", new Integer[] {100, 100});
        mapsSize.put("nameless_i", new Integer[] {340, 340});
        mapsSize.put("alde_gld", new Integer[] {320, 320});
        mapsSize.put("hu_fild02", new Integer[] {400, 400});
        mapsSize.put("payg_cas03", new Integer[] {300, 300});
        mapsSize.put("mag_dun01", new Integer[] {260, 260});
        mapsSize.put("que_rachel", new Integer[] {340, 340});
        mapsSize.put("ra_san05", new Integer[] {300, 300});
        mapsSize.put("tur_dun06", new Integer[] {100, 100});
        mapsSize.put("gl_prison", new Integer[] {200, 200});
        mapsSize.put("yuno_fild11", new Integer[] {400, 400});
        mapsSize.put("yuno_fild05", new Integer[] {400, 400});
        mapsSize.put("gef_fild05", new Integer[] {380, 380});
        mapsSize.put("gef_fild11", new Integer[] {400, 400});
        mapsSize.put("moc_fild17", new Integer[] {400, 400});
        mapsSize.put("ama_in01", new Integer[] {200, 200});
        mapsSize.put("izlude_in", new Integer[] {200, 200});
        mapsSize.put("moc_fild03", new Integer[] {320, 360});
        mapsSize.put("ice_dun04", new Integer[] {200, 200});
        mapsSize.put("cmd_fild02", new Integer[] {400, 400});
        mapsSize.put("um_dun01", new Integer[] {300, 300});
        mapsSize.put("jupe_area1", new Integer[] {164, 300});
        mapsSize.put("ra_fild04", new Integer[] {400, 400});
        mapsSize.put("ra_fild10", new Integer[] {400, 400});
        mapsSize.put("job_sword1", new Integer[] {260, 260});
        mapsSize.put("lhz_in03", new Integer[] {280, 280});
        mapsSize.put("ve_fild06", new Integer[] {400, 400});
        mapsSize.put("c_tower3", new Integer[] {260, 260});
        mapsSize.put("c_tower2", new Integer[] {300, 300});
        mapsSize.put("job_prist", new Integer[] {200, 200});
        mapsSize.put("ve_fild07", new Integer[] {400, 400});
        mapsSize.put("lhz_in02", new Integer[] {300, 300});
        mapsSize.put("jupe_gate", new Integer[] {100, 200});
        mapsSize.put("ra_fild11", new Integer[] {400, 400});
        mapsSize.put("nyd_dun02", new Integer[] {260, 400});
        mapsSize.put("ra_fild05", new Integer[] {400, 400});
        mapsSize.put("cmd_fild03", new Integer[] {400, 400});
        mapsSize.put("moc_fild02", new Integer[] {400, 360});
        mapsSize.put("moc_fild16", new Integer[] {400, 400});
        mapsSize.put("gef_fild10", new Integer[] {400, 400});
        mapsSize.put("gef_fild04", new Integer[] {380, 380});
        mapsSize.put("yuno_fild04", new Integer[] {400, 400});
        mapsSize.put("yuno_fild10", new Integer[] {400, 400});
        mapsSize.put("gonryun", new Integer[] {300, 300});
        mapsSize.put("tha_t01", new Integer[] {300, 300});
        mapsSize.put("tha_t03", new Integer[] {280, 280});
        mapsSize.put("yuno_fild06", new Integer[] {400, 400});
        mapsSize.put("kh_vila", new Integer[] {200, 200});
        mapsSize.put("yuno_fild12", new Integer[] {400, 400});
        mapsSize.put("in_sphinx4", new Integer[] {240, 240});
        mapsSize.put("ein_fild09", new Integer[] {400, 400});
        mapsSize.put("gef_fild12", new Integer[] {400, 400});
        mapsSize.put("gef_fild06", new Integer[] {400, 400});
        mapsSize.put("que_sign01", new Integer[] {240, 240});
        mapsSize.put("moc_fild14", new Integer[] {400, 400});
        mapsSize.put("ama_in02", new Integer[] {240, 200});
        mapsSize.put("prt_sewb4", new Integer[] {200, 200});
        mapsSize.put("um_dun02", new Integer[] {300, 300});
        mapsSize.put("cmd_fild01", new Integer[] {400, 400});
        mapsSize.put("ra_fild13", new Integer[] {400, 400});
        mapsSize.put("jupe_area2", new Integer[] {164, 300});
        mapsSize.put("ra_fild07", new Integer[] {400, 400});
        mapsSize.put("job_priest", new Integer[] {200, 200});
        mapsSize.put("ve_fild05", new Integer[] {400, 400});
        mapsSize.put("job_hunter", new Integer[] {200, 200});
        mapsSize.put("c_tower1", new Integer[] {400, 400});
        mapsSize.put("ve_fild04", new Integer[] {400, 400});
        mapsSize.put("lou_fild01", new Integer[] {400, 400});
        mapsSize.put("prt_church", new Integer[] {200, 200});
        mapsSize.put("lhz_in01", new Integer[] {300, 280});
        mapsSize.put("job_wizard", new Integer[] {200, 200});
        mapsSize.put("ra_in01", new Integer[] {400, 400});
        mapsSize.put("ra_fild06", new Integer[] {400, 400});
        mapsSize.put("ra_fild12", new Integer[] {400, 400});
        mapsSize.put("nyd_dun01", new Integer[] {300, 300});
        mapsSize.put("moc_fild15", new Integer[] {400, 400});
        mapsSize.put("moc_fild01", new Integer[] {400, 400});
        mapsSize.put("jupe_ele_r", new Integer[] {100, 160});
        mapsSize.put("gef_fild07", new Integer[] {380, 380});
        mapsSize.put("in_sphinx5", new Integer[] {200, 200});
        mapsSize.put("gef_fild13", new Integer[] {400, 400});
        mapsSize.put("ein_fild08", new Integer[] {400, 400});
        mapsSize.put("yuno_fild07", new Integer[] {400, 400});
        mapsSize.put("in_rogue", new Integer[] {400, 400});
        mapsSize.put("tha_t02", new Integer[] {300, 300});
        mapsSize.put("yuno_pre", new Integer[] {140, 140});
        mapsSize.put("tha_t06", new Integer[] {240, 240});
        mapsSize.put("tha_t12", new Integer[] {180, 180});
        mapsSize.put("yuno_fild03", new Integer[] {400, 400});
        mapsSize.put("job_knight", new Integer[] {200, 200});
        mapsSize.put("man_in01", new Integer[] {400, 300});
        mapsSize.put("moc_fild05", new Integer[] {400, 400});
        mapsSize.put("moc_fild11", new Integer[] {400, 400});
        mapsSize.put("hugel", new Integer[] {268, 280});
        mapsSize.put("in_sphinx1", new Integer[] {300, 300});
        mapsSize.put("gef_fild03", new Integer[] {400, 400});
        mapsSize.put("cmd_fild04", new Integer[] {400, 400});
        mapsSize.put("juperos_01", new Integer[] {300, 300});
        mapsSize.put("ice_dun02", new Integer[] {300, 300});
        mapsSize.put("prt_sewb1", new Integer[] {320, 320});
        mapsSize.put("louyang", new Integer[] {360, 360});
        mapsSize.put("alb_ship", new Integer[] {200, 200});
        mapsSize.put("ra_fild02", new Integer[] {400, 400});
        mapsSize.put("schg_cas03", new Integer[] {400, 400});
        mapsSize.put("spl_in02", new Integer[] {300, 300});
        mapsSize.put("1@tower", new Integer[] {400, 400});
        mapsSize.put("c_tower4", new Integer[] {216, 216});
        mapsSize.put("abyss_01", new Integer[] {300, 300});
        mapsSize.put("ve_fild01", new Integer[] {400, 400});
        mapsSize.put("yuno", new Integer[] {400, 400});
        mapsSize.put("prt_in", new Integer[] {300, 200});
        mapsSize.put("schg_cas02", new Integer[] {400, 400});
        mapsSize.put("ra_fild03", new Integer[] {400, 400});
        mapsSize.put("einbech", new Integer[] {300, 300});
        mapsSize.put("ice_dun03", new Integer[] {300, 300});
        mapsSize.put("job_hunte", new Integer[] {200, 200});
        mapsSize.put("cmd_fild05", new Integer[] {400, 400});
        mapsSize.put("monk_test", new Integer[] {400, 400});
        mapsSize.put("bat_room", new Integer[] {300, 300});
        mapsSize.put("gef_fild02", new Integer[] {400, 400});
        mapsSize.put("moc_fild10", new Integer[] {400, 320});
        mapsSize.put("moc_fild04", new Integer[] {400, 400});
        mapsSize.put("siege_test", new Integer[] {220, 220});
        mapsSize.put("yuno_fild02", new Integer[] {400, 400});
        mapsSize.put("moc_prydb1", new Integer[] {200, 200});
        mapsSize.put("tha_t07", new Integer[] {140, 200});
        mapsSize.put("orcsdun02", new Integer[] {200, 200});
        mapsSize.put("tha_t11", new Integer[] {180, 180});
        mapsSize.put("ra_temsky", new Integer[] {200, 200});
        mapsSize.put("tha_t05", new Integer[] {240, 240});
        mapsSize.put("moc_fild12", new Integer[] {320, 400});
        mapsSize.put("moc_fild06", new Integer[] {400, 400});
        mapsSize.put("gef_fild00", new Integer[] {400, 400});
        mapsSize.put("in_sphinx2", new Integer[] {300, 300});
        mapsSize.put("gef_fild14", new Integer[] {400, 400});
        mapsSize.put("juperos_02", new Integer[] {300, 300});
        mapsSize.put("cmd_fild07", new Integer[] {400, 400});
        mapsSize.put("prt_sewb2", new Integer[] {200, 200});
        mapsSize.put("ice_dun01", new Integer[] {300, 300});
        mapsSize.put("job_soul", new Integer[] {60, 60});
        mapsSize.put("ra_fild01", new Integer[] {400, 400});
        mapsSize.put("splendide", new Integer[] {400, 400});
        mapsSize.put("spl_in01", new Integer[] {400, 400});
        mapsSize.put("ve_fild03", new Integer[] {400, 400});
        mapsSize.put("1@nyd", new Integer[] {400, 400});
        mapsSize.put("lhz_que01", new Integer[] {120, 180});
        mapsSize.put("abyss_03", new Integer[] {200, 200});
        mapsSize.put("abyss_02", new Integer[] {300, 300});
        mapsSize.put("ve_fild02", new Integer[] {400, 400});
        mapsSize.put("schg_cas01", new Integer[] {400, 400});
        mapsSize.put("prt_sewb3", new Integer[] {200, 200});
        mapsSize.put("cmd_fild06", new Integer[] {400, 400});
        mapsSize.put("in_sphinx3", new Integer[] {240, 240});
        mapsSize.put("kh_school", new Integer[] {200, 200});
        mapsSize.put("gef_fild01", new Integer[] {400, 400});
        mapsSize.put("moc_fild07", new Integer[] {400, 400});
        mapsSize.put("moc_fild13", new Integer[] {340, 400});
        mapsSize.put("prt_are01", new Integer[] {300, 300});
        mapsSize.put("yuno_fild01", new Integer[] {400, 400});
        mapsSize.put("tha_t04", new Integer[] {280, 280});
        mapsSize.put("tha_t10", new Integer[] {180, 180});
        mapsSize.put("orcsdun01", new Integer[] {200, 200});
    }

    // Attribute
    private int id;
    private String slug;
    private String name;

    public static class Builder extends DBLoadObject {

        private int id;
        public Builder(int castle_id) {
            super(TABLE_NAME, ROMap.class);
            this.id = castle_id;
        }

        public ROMap build() {
            ROMap c = (ROMap) load(TABLE_KEY, id);
            return c;
        }

    }

    private ROMap() {

    }

    public int getId() {
        return id;
    }

    public String getSlug() {
        return slug;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "{\"_class\":\"ROMap\", " +
                "\"id\":\"" + id + "\"" + ", " +
                "\"slug\":" + (slug == null ? "null" : "\"" + slug + "\"") + ", " +
                "\"name\":" + (name == null ? "null" : "\"" + name + "\"") +
                "}";
    }
}
