export class CurrencyFormatValueConverter {
  toView(value) {
    return "€" + value.toFixed(2);
  }
}
