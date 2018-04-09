import moment from "moment";
import et from "moment/locale/et";

export class DateFormatValueConverter {
  toView(value) {
    let m = moment(value, "YYYY-MM-DD");
    m.locale("et", et);
    return m.format("LL");
  }
}
