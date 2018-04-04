import moment from 'moment';

export class DateFormatValueConverter {
  toView(value) {
    return moment(value, "YYYY-MM-DD").format("dddd, Do MMMM YYYY");
  }
}
