function defaultFormatter(i){var a=!!i.uri,o='<div class="layout-item-box">';return o+='<div class="item-group fill-group">',o+='<div class="item-titolo item-valore">'+i.titolo+"</div>",o+='<div class="item-sottotitolo item-label">'+i.sottotitolo+"</div>",o+="</div>",a&&(o+='<div><iron-icon icon="chevron-right"></iron-icon></div>'),o+="</div>"}function versamenti1(i){var a="<div>";return a+=i.sottotitolo,a+="</div>",a+="<div secondary>",a+=i.titolo,a+="</div>"}function versamenti2(i){var a="icons:check-circle",o=_getEtichetta(i.voci.statoVersamento,!1);"NON_ESEGUITO"===getValore(i.voci.statoVersamento)&&(a="icons:radio-button-unchecked"),"ANNULLATO"===getValore(i.voci.statoVersamento)&&(a="icons:cancel"),"ANOMALO"===getValore(i.voci.statoVersamento)&&(a="icons:report-problem");var t='<div class="layout-item-box">';return t+='<div class="icon" title="'+o+'"><iron-icon icon="'+a+'"></iron-icon></div>',t+='<div class="layout-group">',t+='<div class="label-group">',t+='<div class="item-group"><span class="item-label">'+getEtichetta(i.voci.codDominio)+"</span>",t+='<span class="item-valore">'+getValore(i.voci.codDominio)+"</span></div>",t+='<div class="item-group"><span class="item-label">'+getEtichetta(i.voci.id)+"</span>",t+='<span class="item-valore">'+getValore(i.voci.id)+"</span></div>",t+='<div class="item-group"><span class="item-label">'+getEtichetta(i.voci.cf)+"</span>",t+='<span class="item-valore">'+getValore(i.voci.cf)+"</span></div>",t+="</div>",t+='<div class="label-group">',t+='<div class="item-group"><span class="item-label">'+getEtichetta(i.voci.importoTotale)+"</span>",t+='<span class="item-valore">'+getValore(i.voci.importoTotale)+"</span></div>",t+='<div class="item-group"><span class="item-label">'+getEtichetta(i.voci.iuv)+"</span>",t+='<span class="item-valore">'+getValore(i.voci.iuv)+"</span></div>",t+='<div class="item-group"><span class="item-label">'+getEtichetta(i.voci.scadenza)+"</span>",t+='<span class="item-valore">'+getValore(i.voci.scadenza)+"</span></div>",t+="</div>",t+="</div>",t+='<div><iron-icon icon="chevron-right"></iron-icon></div>',t+="</div>"}function flussoRendicontazioni1(i){var a="icons:check-circle",o=_getEtichetta(i.voci.stato,!1);"ACCETTATA"!=getValore(i.voci.stato)&&(a="icons:report-problem");var t='<div class="layout-item-box">';t+='<div class="icon" title="'+o+'"><iron-icon icon="'+a+'"></iron-icon></div>',t+='<div class="layout-group">',t+='<div class="label-group">',t+='<div class="item-group"><span class="item-label">'+getEtichetta(i.voci.codFlusso)+"</span>",t+='<span class="item-valore">'+getValore(i.voci.codFlusso)+"</span></div>",t+='<div class="item-group"><span class="item-label">'+getEtichetta(i.voci.idDominio)+"</span>",t+='<span class="item-valore">'+getValore(i.voci.idDominio)+"</span></div>",t+='<div class="item-group"><span class="item-label">'+getEtichetta(i.voci.psp)+"</span>",t+='<span class="item-valore">'+getValore(i.voci.psp)+"</span></div>",t+='<div class="item-group"><span class="item-label">'+getEtichetta(i.voci.trn)+"</span>",t+='<span class="item-valore">'+getValore(i.voci.trn)+"</span></div>",t+="</div>",t+='<div class="label-group">',t+='<div class="item-group"><span class="item-label">'+getEtichetta(i.voci.numRendicontazioniOk)+"</span>",t+='<span class="item-valore">'+getValore(i.voci.numRendicontazioniOk)+"</span></div>";var e=getValore(i.voci.numRendicontazioniAnomale);e&&e>0&&(t+='<div class="item-group"><span class="item-label">'+getEtichetta(i.voci.numRendicontazioniAnomale)+"</span>",t+='<span class="item-valore">'+e+"</span></div>");var s=getValore(i.voci.numRendicontazioniAltroIntermediario);return s&&s>0&&(t+='<div class="item-group"><span class="item-label">'+getEtichetta(i.voci.numRendicontazioniAltroIntermediario)+"</span>",t+='<span class="item-valore">'+s+"</span></div>"),t+="</div>",t+="</div>",t+='<div><iron-icon icon="chevron-right"></iron-icon></div>',t+="</div>"}function rendicontazioni1(i){var a="icons:check-circle",o=_getEtichetta(i.voci.stato,!1);"3"===getValore(i.voci.esito)&&(a="icons:check-circle"),"ANOMALA"===getValore(i.voci.stato)&&(a="icons:report-problem"),"ALTRO_INTERMEDIARIO"===getValore(i.voci.stato)&&(a="icons:radio-button-unchecked");var t='<div class="layout-item-box">';if(t+='<div class="icon" title="'+o+'"><iron-icon icon="'+a+'"></iron-icon></div>',i.voci.anomalie&&(t+='<div class="layout-anomalie">'),t+='<div class="layout-group">',t+='<div class="label-group">',t+='<div class="item-group"><span class="item-label">'+getEtichetta(i.voci.dominio)+"</span>",t+='<span class="item-valore">'+getValore(i.voci.dominio)+"</span></div>",t+='<div class="item-group"><span class="item-label">'+getEtichetta(i.voci.iuv)+"</span>",t+='<span class="item-valore">'+getValore(i.voci.iuv)+"</span></div>",t+='<div class="item-group"><span class="item-label">'+getEtichetta(i.voci.iur)+"</span>",t+='<span class="item-valore">'+getValore(i.voci.iur)+"</span></div>",t+="</div>",t+='<div class="label-group">',t+='<div class="item-group"><span class="item-label">'+getEtichetta(i.voci.importo)+"</span>",t+='<span class="item-valore">'+getValore(i.voci.importo)+"</span></div>",t+='<div class="item-group"><span class="item-label">'+getEtichetta(i.voci.data)+"</span>",t+='<span class="item-valore">'+getValore(i.voci.data)+"</span></div>",t+='<div class="item-group"><span class="item-label">'+getEtichetta(i.voci.codSingoloVersamento)+"</span>",t+='<span class="item-valore">'+getValore(i.voci.codSingoloVersamento)+"</span></div>",t+="</div>",t+="</div>",i.voci.anomalie){t+='<div class="anomalie-group">',t+='<span class="item-label">'+_getEtichetta(i.voci.anomalie,!1)+"</span>";var e=getVoci(i.voci,"anomalie_");for(var s in e)t+='<div class="item-group"><span class="item-label">'+getEtichetta(e[s])+"</span>",t+='<span class="item-valore">'+getValore(e[s])+"</span></div>";t+="</div>",t+="</div><!-- anomalie-group -->"}return i.uri&&""!=i.uri&&(t+='<div><iron-icon icon="chevron-right"></iron-icon></div>'),t+="</div>"}function transazioni1(i){var a="icons:check-circle",o=_getEtichetta(i.voci.statoTransazione,!1);"rr"===getValore(i.voci.tipo)&&(a="icons:check-circle"),"finaleKo"===getValore(i.voci.stato)&&(a="icons:check-circle"),"inCorso"===getValore(i.voci.stato)&&(a="icons:radio-button-unchecked");var t='<div class="layout-item-box">';return t+='<div class="icon" title="'+o+'"><iron-icon icon="'+a+'"></iron-icon></div>',t+='<div class="layout-group">',t+='<div class="label-group">',t+='<div class="item-group"><span class="item-label">'+getEtichetta(i.voci.data)+"</span>",t+='<span class="item-valore">'+getValore(i.voci.data)+"</span></div>",t+='<div class="item-group"><span class="item-label">'+getEtichetta(i.voci.iuv)+"</span>",t+='<span class="item-valore">'+getValore(i.voci.iuv)+"</span></div>",t+='<div class="item-group"><span class="item-label">'+getEtichetta(i.voci.ccp)+"</span>",t+='<span class="item-valore">'+getValore(i.voci.ccp)+"</span></div>",t+="</div>",t+='<div class="label-group">',t+='<div class="item-group"><span class="item-label">'+getEtichetta(i.voci.importo)+"</span>",t+='<span class="item-valore">'+getValore(i.voci.importo)+"</span></div>",t+='<div class="item-group"><span class="item-label">'+getEtichetta(i.voci.psp)+"</span>",t+='<span class="item-valore">'+getValore(i.voci.psp)+"</span></div>",t+='<div class="item-group"><span class="item-label">'+getEtichetta(i.voci.canale)+"</span>",t+='<span class="item-valore">'+getValore(i.voci.canale)+"</span></div>",t+="</div>",t+="</div>",t+='<div><iron-icon icon="chevron-right"></iron-icon></div>',t+="</div>"}function pagamenti1(i){var a="icons:check-circle",o=_getEtichetta(i.voci.statoPagamento,!1);"revocato"===getValore(i.voci.statoPagamento)&&(a="icons:check-circle");var t='<div class="layout-item-box">';return t+='<div class="icon" title="'+o+'"><iron-icon icon="'+a+'"></iron-icon></div>',t+='<div class="layout-group">',t+='<div class="label-group">',t+='<div class="item-group"><span class="item-label">'+getEtichetta(i.voci.codSingoloVersamentoEnte)+"</span>",t+='<span class="item-valore">'+getValore(i.voci.codSingoloVersamentoEnte)+"</span></div>",t+='<div class="item-group"><span class="item-label">'+getEtichetta(i.voci.iur)+"</span>",t+='<span class="item-valore">'+getValore(i.voci.iur)+"</span></div>",t+="</div>",t+='<div class="label-group">',t+='<div class="item-group"><span class="item-label">'+getEtichetta(i.voci.importoPagato)+"</span>",t+='<span class="item-valore">'+getValore(i.voci.importoPagato)+"</span></div>",t+='<div class="item-group"><span class="item-label">'+getEtichetta(i.voci.dataPagamento)+"</span>",t+='<span class="item-valore">'+getValore(i.voci.dataPagamento)+"</span></div>",t+="</div>",t+="</div>",t+='<div><iron-icon icon="chevron-right"></iron-icon></div>',t+="</div>"}function estrattoContoPagamenti1(i){var a="icons:check-circle",o=_getEtichetta(i.voci.statoVersamento,!1);"ANOMALO"===getValore(i.voci.statoVersamento)&&(a="icons:report-problem"),"revocato"===getValore(i.voci.statoPagamento)&&(a="icons:check-circle");var t='<div class="layout-item-box">';return t+='<div class="icon" title="'+o+'"><iron-icon icon="'+a+'"></iron-icon></div>',t+='<div class="layout-group">',t+='<div class="label-group">',t+='<div class="item-group"><span class="item-label">'+getEtichetta(i.voci.codSingoloVersamentoEnte)+"</span>",t+='<span class="item-valore">'+getValore(i.voci.codSingoloVersamentoEnte)+"</span></div>",t+='<div class="item-group"><span class="item-label">'+getEtichetta(i.voci.iur)+"</span>",t+='<span class="item-valore">'+getValore(i.voci.iur)+"</span></div>",t+="</div>",t+='<div class="label-group">',t+='<div class="item-group"><span class="item-label">'+getEtichetta(i.voci.importoPagato)+"</span>",t+='<span class="item-valore">'+getValore(i.voci.importoPagato)+"</span></div>",t+='<div class="item-group"><span class="item-label">'+getEtichetta(i.voci.dataPagamento)+"</span>",t+='<span class="item-valore">'+getValore(i.voci.dataPagamento)+"</span></div>",t+="</div>",t+="</div>",t+='<div><iron-icon icon="chevron-right"></iron-icon></div>',t+="</div>"}function domini1(i){var a="icons:check-circle",o=_getEtichetta(i.voci.stato,!1);"disabilitato"!==getValore(i.voci.stato)&&"nonVerificato"!==getValore(i.voci.stato)||(a="icons:radio-button-unchecked"),"errore"===getValore(i.voci.stato)&&(a="icons:report-problem");var t='<div class="layout-item-box">';return t+='<div class="icon" title="'+o+'"><iron-icon icon="'+a+'"></iron-icon></div>',t+='<div class="label-group fill-group">',t+='<div class="item-group fill-group"><span class="item-label">'+getEtichetta(i.voci.ragioneSociale)+"</span>",t+='<span class="item-valore">'+getValore(i.voci.ragioneSociale)+"</span></div>",t+='<div class="item-group fill-group"><span class="item-label">'+getEtichetta(i.voci.codDominio)+"</span>",t+='<span class="item-valore">'+getValore(i.voci.codDominio)+"</span></div>",t+='<div class="item-group fill-group"><span class="item-label">'+getEtichetta(i.voci.codIntermediario)+"</span>",t+='<span class="item-valore">'+getValore(i.voci.codIntermediario)+"</span></div>",i.voci.anomalia&&(t+='<div class="item-group fill-group"><span class="item-label">'+getEtichetta(i.voci.anomalia)+"</span>",t+='<span class="item-valore">'+getValore(i.voci.anomalia)+"</span></div>"),t+="</div>",t+='<div><iron-icon icon="chevron-right"></iron-icon></div>',t+="</div>"}function stazioni1(i){var a="icons:check-circle",o=_getEtichetta(i.voci.stato,!1);"disabilitato"!==getValore(i.voci.stato)&&"nonVerificato"!==getValore(i.voci.stato)||(a="icons:radio-button-unchecked"),"errore"===getValore(i.voci.stato)&&(a="icons:report-problem");var t='<div class="layout-item-box">';return t+='<div class="icon" title="'+o+'"><iron-icon icon="'+a+'"></iron-icon></div>',t+='<div class="layout-group">',t+='<div class="label-group fill-group">',t+='<div class="item-group fill-group"><span class="item-valore">'+_getEtichetta(i.voci.codStazione,!1)+"</span></div>",i.voci.anomalia&&(t+='<div class="item-group fill-group"><span class="item-label">'+getEtichetta(i.voci.anomalia)+"</span>",t+='<span class="item-valore">'+getValore(i.voci.anomalia)+"</span></div>"),t+="</div>",t+="</div>",t+='<div><iron-icon icon="chevron-right"></iron-icon></div>',t+="</div>"}function psp1(i){var a="icons:check-circle",o=_getEtichetta(i.voci.stato,!1);"disabilitato"!==getValore(i.voci.stato)&&"nonVerificato"!==getValore(i.voci.stato)||(a="icons:radio-button-unchecked"),"errore"===getValore(i.voci.stato)&&(a="icons:report-problem");var t='<div class="layout-item-box">';return t+='<div class="icon" title="'+o+'"><iron-icon icon="'+a+'"></iron-icon></div>',t+='<div class="layout-group">',t+='<div class="label-group fill-group">',t+='<div class="item-group fill-group"><span class="item-label">'+getEtichetta(i.voci.ragioneSociale)+"</span>",t+='<span class="item-valore">'+getValore(i.voci.ragioneSociale)+"</span></div>",t+='<div class="item-group fill-group"><span class="item-label">'+getEtichetta(i.voci.codPsp)+"</span>",t+='<span class="item-valore">'+getValore(i.voci.codPsp)+"</span></div>",t+="</div>",t+="</div>",t+='<div><iron-icon icon="chevron-right"></iron-icon></div>',t+="</div>"}function getEtichetta(i){return _getEtichetta(i,!0)}function _getEtichetta(i,a){return i?i.valore&&a?i.etichetta+": ":i.etichetta:""}function getValore(i){return i&&i.valore?i.valore:""}function getVoci(i,a){var o=[],t=Object.keys(i);for(var e in t){var s=t[e];s.startsWith(a)&&o.push(i[s])}return o}