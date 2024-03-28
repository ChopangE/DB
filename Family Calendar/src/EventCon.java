//event정보들을 담는 container
public class EventCon {
    public int event_id,year,month,day,hour,min,duration,interval,timeframe,owner;
    public String location,content;

    public EventCon(int []arr1, String []arr2){
        event_id = arr1[0];
        year = arr1[1];
        month = arr1[2];
        day = arr1[3];
        hour = arr1[4];
        min = arr1[5];
        duration = arr1[6];
        interval = arr1[7];
        timeframe = arr1[8];
        owner = arr1[9];
        location = arr2[0];
        content = arr2[1];
    }
    public EventCon(int event_id, int year, int month, int day, int hour, int min, int duration, int interval, int timeframe, int owner, String location, String content) {
        this.event_id = event_id;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.min = min;
        this.duration = duration;
        this.interval = interval;
        this.timeframe = timeframe;
        this.owner = owner;
        this.location = location;
        this.content = content;
    }

    boolean check(int y , int m, int d){
        if(this.year == y && this.month == m && this.day == d){
            return true;
        }
        return false;
    }
}
