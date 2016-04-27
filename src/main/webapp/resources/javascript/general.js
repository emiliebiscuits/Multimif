function isEmail(email) {
    var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    return regex.test(email);
}
function isWebsite(url){
    var regex = /(http|https):\/\/(\w+:{0,1}\w*@)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%@!\-\/]))?/;
    return regex.test(url);
}
/* 
    Ajout des css Erreur ou Success pour les champs Input 
*/
$.fn.extend({
  hasError: function(msg) {
    return this.each(function() {
        ($(this).parent()).addClass('has-error').find('.help-block').html(msg).show();
    });
  },
  hasSucces: function() {
    return this.each(function() {
      ($(this).parent()).removeClass('has-error').find('.help-block').hide();
    });
  }
});


/*
    message temporaire en haut de l'écran
    type: success/danger/warning/info
    msg: le message a afficher
    titre: un titre éventuel
    confirm: si true, attente action utilisateur pour disparaitre

    ex: alertModif("success", "Modification bien prise en compte", false, "ok");
*/
function alertModif(type, msg, confirm, titre){
    titre = titre || "";

    var alert_div = document.createElement('div');
    $(alert_div).addClass('alert').addClass('alert-'+type+'').addClass('alert-dismissible').addClass('alert_modif').addClass('text-center');
    $(alert_div).data("role", "alert");

    var texte = '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button><strong class="pull-left">'+titre+'</strong> '+msg;                    
    $(alert_div).html(texte);
    $(alert_div).appendTo('body').slideDown(400);
    if(confirm === false){
        $(alert_div).delay(5000).slideUp(400);
    }
}

var getUrlParameter = function getUrlParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
};