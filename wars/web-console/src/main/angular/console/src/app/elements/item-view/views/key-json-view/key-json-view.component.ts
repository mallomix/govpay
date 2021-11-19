import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { DatoEx } from '../../../../classes/view/dato-ex';

declare let GovRiconciliazioniConfig: any;

@Component({
  selector: 'link-key-json-view',
  templateUrl: './key-json-view.component.html',
  styleUrls: ['./key-json-view.component.scss']
})
export class KeyJsonViewComponent implements OnInit, OnChanges {

  @Input('info') info: DatoEx = new DatoEx();

  quoteKeys: any[] = [];

  // Default
  _quoteOrder = ['titolo', 'tipologia', 'categoria', 'capitolo', 'articolo', 'accertamento', 'annoEsercizio', 'importo'];
  _quoteLabel = {
    capitolo: 'Capitolo',
    annoEsercizio: 'Anno esercizio',
    importo: 'Importo',
    titolo: 'Titolo',
    accertamento: 'Accertamento',
    tipologia: 'Tipologia',
    categoria: 'Categoria',
    articolo: 'Articolo',
    proprietaCustom: 'Proprieta custom'
  };

  constructor() {
    if (GovRiconciliazioniConfig && GovRiconciliazioniConfig.quoteOrder && GovRiconciliazioniConfig.quoteLabel) {
      this._quoteOrder = GovRiconciliazioniConfig.quoteOrder;
      this._quoteLabel = GovRiconciliazioniConfig.quoteLabel;
    }
  }

  ngOnInit() {
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes.info && changes.info.currentValue) {
      this.prepareData();
    }
  }

  prepareData() {
    if ((this.info.type == 'quote') && this.info.value && this.info.value[0]) {
      this.quoteKeys = Object.keys(this.info.value[0]);
      const sorted = this.quoteKeys.sort((a, b) => this._quoteOrder.indexOf(a) - this._quoteOrder.indexOf(b));
      this.quoteKeys = sorted;
    }
  }

  // protected _setLabelStyle() {
  //   let _trunc: boolean = true;
  //   if(this.info && this.info.value && this.info.value.indexOf(' ') != -1) {
  //     _trunc = false;
  //   }
  //   return {
  //     'text-truncate': _trunc,
  //     'color-gray': true,
  //     'my-0': true
  //   }
  // }

  // protected _tooltip(): string {
  //   if(this.info) {
  //     return this.info.label+': '+this.info.value;
  //   }
  //   return null;
  // }

}
