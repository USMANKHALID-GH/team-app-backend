package com.zalisoft.teamapi.enums;

public enum ResponseMessageEnum {
    BACK_SYSTEM_ERROR_MSG_001("back.system.error.msg.001", "Sistemsel bir hata alınmıştır, Daha sonra tekrar deneyiniz."),
    BACK_USER_MSG_001("back.user.msg.001", "Kullanıcı bulunamamıştır."),
    BACK_USER_MSG_002("back.user.msg.002", "Bu Telefon Sistemde Kayıtlıdır."),
    BACK_USER_MSG_003("back.user.msg.003", "Bu Mail Sistemde Kayıtlıdır."),
    BACK_USER_MSG_004("back.user.msg.004", "Bu kullanıcı aktif değildir."),
    BACK_USER_MSG_005("back.user.msg.005", "Bu Mail girilmeli."),
    BACK_USER_MSG_06("back.user.msg.006", "Email veya şifre hatalı."),
    BACK_USER_MSG_007("back.user.msg.007", "TC kimlik numarası girilmelir."),
    BACK_USER_MSG_008("back.user.msg.008", "Kullanici isim girilmeli."),
    BACK_USER_MSG_009("back.user.msg.009", "Kullanici soyisim  girilmeli."),
    BACK_USER_MSG_010("back.user.msg.010", "Kullanici title girilmeli."),
    BACK_USER_MSG_011("back.user.msg.011", "Kullanici senelik tecrubesi girilmeli."),

    BACK_PRIVILEGE_MSG_001("back.privilege.msg.001", "Bu isim mevcut değildir."),
    BACK_PRIVILEGE_MSG_002("back.privilege.msg.002", "Privilege mevcut değildir."),
    BACK_PRIVILEGE_MSG_003("back.privilege.msg.003", "Privilege isim girilmeli."),


    BACK_ROLE_MSG_001("back.role.msg.001", "Rol Tanımı bulunamamıştır."),
    BACK_ROLE_MSG_002("back.role.msg.002", "Bu isimde rol tanımı var, farklı bir isim giriniz."),
    BACK_ROLE_MSG_003("back.role.msg.003", "Rolü, eklenmiş olan kullanıcılardan çıkardıktan sonra silebilirsiniz."),
    BACK_ROLE_MSG_004("back.role.msg.004", "Rol isim mevcuttur."),


    BACK_PROJECT_MSG_001("back.project.msg.001", "Project isim girilmeli "),
    BACK_PROJECT_MSG_002("back.project.msg.002", "Project son tarih girilmeli "),
    BACK_PROJECT_MSG_003("back.project.msg.003", "Project baslangic tarih girilmeli"),
    BACK_PROJECT_MSG_004("back.project.msg.004", "Project mevcut degildir"),
    BACK_PROJECT_MSG_005("back.project.msg.004", "Sadece Project manager degisik yapabilir"),


    BACK_TEAM_MSG_001("back.team.msg.001", "Team isim girilmeli "),
    BACK_TEAM_MSG_002("back.team.msg.002", "Team member atanma sadece captain yapiliyor"),
    BACK_TEAM_MSG_003("back.team.msg.003", "Team  mevcut degildir"),
    BACK_TEAM_MSG_004("back.team.msg.004", "Team member cikarilma sadece captain yapiliyor"),
    BACK_TEAM_MSG_005("back.team.msg.005", "Team member mevcuttur"),

    BACK_TASK_MSG_001("back.task.msg.001", "Task isim  girilmeli"),
    BACK_TASK_MSG_002("back.task.msg.002", "Task description girilmeli"),
    BACK_TASK_MSG_003("back.task.msg.003", "Task status  girilmeli"),
    BACK_TASK_MSG_004("back.task.msg.004", "Task son tarih   girilmeli"),
    BACK_TASK_MSG_005("back.task.msg.005", "Task  mevcut degildir"),
    BACK_TASK_MSG_006("back.task.msg.006", "Sadece project yada captain manager degisik yapabilir"),


    BACK_REPORT_MSG_001("back.report.msg.001", "Report  mevcut degildir"),
    BACK_REPORT_MSG_002("back.report.msg.002", "Report  minute boş bırakılmaz"),
    BACK_REPORT_MSG_003("back.report.msg.003", "Report  details boş bırakılmaz"),
    BACK_REPORT_MSG_004("back.report.msg.004", "Haftada bir kere izin aliniyor"),
    BACK_REPORT_MSG_005("back.report.msg.005", "Gunde bir kere raport atilir"),

    BACK_PARAMETER_MSG_001("back.parameter.msg.001", "Parameter key mevcut degil"),
    BACK_PARAMETER_MSG_002("back.parameter.msg.003", "Parameter key girilmeli"),
    BACK_PARAMETER_MSG_003("back.parameter.msg.003", "Parameter description girilmeli"),
    BACK_PARAMETER_MSG_004("back.parameter.msg.004", "Parameter mevcut degildir"),
    BACK_PARAMETER_MSG_005("back.parameter.msg.005", "Parameter key mevcuttur"),


    BACK_CAUTION_MSG_001("back.caution.msg.001", "Caution  mevcut degil"),
    BACK_CAUTION_MSG_002("back.caution.msg.002", "Caution  isim bos birakilmaz"),
    BACK_CAUTION_MSG_003("back.caution.msg.003", "Caution  mesaj bos birakilmaz"),


    BACK_PERMISSION_MSG_001("back.permission.msg.001", "Permission  amaci girilmeli"),
    BACK_PERMISSION_MSG_002("back.permission.msg.002", "Permission baslangic tarih girilmeli"),
    BACK_PERMISSION_MSG_003("back.permission.msg.003", "Permission bitis tarih girilmeli"),
    BACK_PERMISSION_MSG_004("back.permission.msg.004", "Permission mevcut degildir"),
    BACK_PERMISSION_MSG_005("back.permission.msg.005", "Bu permission islem sadece Captainlar yapilir"),
    BACK_PERMISSION_MSG_006("back.permission.msg.006", "Bu permission islem sadece current user yapilir"),


    BACK_CAPTAIN_REPORT_MSG_001("back.capatin.report.msg.001", "Gunde bir kere raport atilir"),

    BACK_CAPTAIN_REPORT_MSG_002("back.capatin.report.msg.002", "Captain report mevcut degildir"),
    BACK_CAPTAIN_REPORT_MSG_003("back.capatin.report.msg.003", "Bu islem sadece captain yada project manager yapilmaktadir"),
    BACK_CAPTAIN_REPORT_MSG_004("back.capatin.report.msg.004", "Captain report sadece MONDAY,TUESDAY,THURSDAY rapor atiliyor"),

    BACK_IMAGE_MSG_001("back.image.001","Image {.jpg, .jpeg, .png, .gif, .bmp, .tiff}"),


    BACK_CURRENT_USER_MSG_001("back.current_user.msg.001", "Sadece current user islem yapabilir"),
    ;
    private final String message;
    private final String messageDetail;

    ResponseMessageEnum(String message, String messageDetail) {
        this.message = message;
        this.messageDetail = messageDetail;
    }

    public String message() {
        return this.message;
    }

    public String messageDetail() {
        return this.messageDetail;
    }
}
