String.prototype.endsWith||(String.prototype.endsWith=function(e,t){var o=this.toString();(void 0===t||t>o.length)&&(t=o.length),t-=e.length;var n=o.indexOf(e,t);return n!==-1&&n===t});var REST_SERVER=location.protocol+'//'+location.hostname+(location.port ? ':'+location.port: ''),BASE_URL=location.pathname;BASE_URL.endsWith("/index.html")&&(BASE_URL=BASE_URL.substring(0,BASE_URL.length-11)),BASE_URL.endsWith("/")&&(BASE_URL=BASE_URL.substring(0,BASE_URL.length-1));var LOGIN_URL=BASE_URL,REST_URL=REST_SERVER+BASE_URL+"/rs",MENU_URL=REST_URL+"/dars/",USER_INFO_URL=REST_URL+"/dars/operatori/user";!function(e){"use strict";var t=e.querySelector("#app");t.showButton=!1,t.toastDuration=3e3,t.USER_LOGGED=null,t.ADVANCED_MODE=!1,t.numSpinner=0,t.LANGUAGE="it",t.RESOURCES={},t.baseUrl="/",""===window.location.port,t.openDrawer=function(e){this.$.drawerPanel.openDrawer()},t.closeDrawer=function(e){this.$.drawerPanel.closeDrawer()},t.scrollPageToTop=function(){},t.showSpinner=function(){t.numSpinner++,this.$.spinner.setSpinner(!0)},t.hideSpinner=function(){t.numSpinner--,0==t.numSpinner&&this.$.spinner.setSpinner(!1)},t.showPaperToast=function(o,n){this.$.toast.toggleClass("fit-bottom",!1),this.$.toast.toggleClass("success",!1),this.$.toast.toggleClass("error",!1),this.$.toast.toggleClass("warning",!1),n&&(this.$.toast.toggleClass(n,!0),this.$.toast.toggleClass("fit-bottom",!0)),Polymer.updateStyles(),this.$.toast.text="",this.$.messages.innerHTML="",t.showButton="error"==n||"warning"==n,t.toastDuration=t.showButton?0:3e3,o.constructor===Array?o.forEach(function(t){var o=e.createElement("p");o.innerHTML=t?t:"",t&&this.$.messages.appendChild(o)}.bind(this)):this.$.toast.text=o,this.$.toast.show()},t.setFocus=function(){if(t.showButton){var e=this.$.toast.querySelector(".paper-toast-button");e.focus()}},t.closeToast=function(){this.$.toast.hide()},t.displayInstalledToast=function(){Polymer.dom(e).querySelector("platinum-sw-cache").disabled||Polymer.dom(e).querySelector("#caching-complete").show()},t.addEventListener("dom-change",function(){console.log("Our app is ready to rock!"),t.getUserInfo()}),window.addEventListener("WebComponentsReady",function(){}),t.getUserInfo=function(){this.$.getUserInfo.url=USER_INFO_URL,this.$.getUserInfo.generateRequest()},t.getUserInfo_responseHandler=function(e){var o=e.detail.response;switch(o.esitoOperazione){case"ESEGUITA":t.USER_LOGGED=e.detail.response.response,t.getConsoleInfo();break;case"NONESEGUITA":window.location=t.getDoLogoutURL();break;case"ERRORE":window.location=t.getDoLogoutURL()}},t.getUserInfo_errorHandler=function(e){this.dars_errorHandler(e)},t.getConsoleInfo=function(){this.$.getConsoleInfo.url=MENU_URL,this.$.getConsoleInfo.generateRequest()},t.getConsoleURL=function(){return REST_URL},t.getLoginURL=function(){return REST_SERVER+BASE_URL+"/"},t.getLogoutOkURL=function(){return REST_SERVER+BASE_URL+"/?msg=lo"},t.getNonAutorizzatoURL=function(){return REST_SERVER+BASE_URL+"/?msg=na"},t.getSessioneScadutaURL=function(){return REST_SERVER+BASE_URL+"/?msg=se"},t.getDoLogoutURL=function(){return REST_SERVER+BASE_URL+"/logout?msg=er"},t.getConsoleInfo_responseHandler=function(o){var n="";o.detail.response.response.menu.home?(this.$.mainModule.title=o.detail.response.response.menu.home.label,this.$.mainModule.resource=o.detail.response.response.menu.home.uri,n=o.detail.response.response.menu.home.uri):this.$.mainModule.title=o.detail.response.response.titolo,o.detail.response.response.lingue&&(t.RESOURCES=o.detail.response.response.lingue,e.dispatchEvent(new CustomEvent("change-language")));for(var s=0;s<o.detail.response.response.menu.sezioni.length;s++){var r=o.detail.response.response.menu.sezioni[s];r.vociMenu&&r.vociMenu.length>0?(r.haVoci=!0,r.opened=!1):(r.haVoci=!1,r.opened=!1);for(var i=0;i<r.vociMenu.length;i++){var a=r.vociMenu[i];a.uri==n&&(r.opened=!0,r.selected=i)}}t.consoleInfo=o.detail.response.response},t.getConsoleInfo_errorHandler=function(e){this.dars_errorHandler(e)},t.toggleMenu=function(e){for(var o=e.model.index,n=0;n<t.consoleInfo.menu.sezioni.length;n++){t.consoleInfo.menu.sezioni[n];n==o?t.set("consoleInfo.menu.sezioni."+n+".opened",!e.model.item.opened):t.set("consoleInfo.menu.sezioni."+n+".opened",!1)}},t.loadModule=function(o){window.linkIntervals&&(window.linkIntervals.forEach(function(e){clearInterval(e)}),window.linkIntervals=[]);for(var n=this.$.mainMenu.selectedClass,s=this.$.mainMenu.querySelectorAll('iron-collapse paper-item[class*="'+n+'"]'),r=0;r<s.length;r++)this.toggleClass(n,!1,s[r]);switch(this.toggleClass(n,!0,o.currentTarget),this.$.mainModule.title=o.target.dataItem.label,o.target.dataItem.tipo){case"statistica":this.$.mainModule.chart=o.target.dataItem;break;default:this.$.mainModule.resource=o.target.dataItem.uri;var i=e.querySelector("link-chart");i&&this.$.mainModule.fromChart()}t.closeDrawer()},t.dars_errorHandler=function(e){t.showPaperToast("Errore","error");var o=e.detail.request.status;401==o?window.location=t.getNonAutorizzatoURL():403==o?window.location=t.getSessioneScadutaURL():500==o&&(window.location=t.getDoLogoutURL())},t.appendLangParameter=function(e){var o=e.indexOf("?")>-1?"&":"?";return o+"lang="+t.LANGUAGE},t.showAbout=function(){e.querySelector("link-info-action").showAbout()}}(document);