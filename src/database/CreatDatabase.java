package database;

import com.Edge;
import com.ScenicSpot;

import java.util.Properties;
import java.io.Console;
import java.sql.*;

//创建数据库
public class CreatDatabase {
	private String driver = "org.apache.derby.jdbc.EmbeddedDriver";
	private String protocol = "jdbc:derby:";
	private String dbName = "TravelSystem";

	public CreatDatabase() {

	}

	public void createDatabase() throws SQLException,ClassNotFoundException{
    	//先创建好景点，方便后续插入
        ScenicSpot[] scenicSpots={
                new ScenicSpot("北门","北门海拔1460米，位于此山最危耸之处,上即绝顶。" +
                        "它建在飞龙岩与翔凤岭之间的低坳处，双峰夹峙，仿佛天门自开。元中统五年（1264年）布山道士张志纯创建" +
                        "门为阁楼式建筑，石砌拱形门洞，额题“北门”。红墙点缀，黄色琉璃瓦盖顶，气势雄伟。" +
                        "门侧有楹联曰“门辟九霄仰步三天胜迹；阶崇万级俯临千嶂奇观",12,"北门.jpg","有","无"),
                new ScenicSpot("狮子山","狮子山古称铜精山。区内东、西狮子山两峰相峙，状若一对雄师，地区因其得名。" +
                        "狮子山区历史悠久、人文荟萃。春秋战国时属吴、越之地，采冶铜的历史始于商周，盛于汉唐，" +
                        "采选铜历史绵延至今不断，李白、王安石、苏东坡等名士曾游历流连于此。",14,"狮子山.jpg","无","有"),
                new ScenicSpot("仙云石","平天矼西端的群峰中，有一巨石耸立在岩石平台上。巨石高12米、长7.5米、宽2.5米；" +
                        "其下的岩石平台长12至15米，宽8至10米，厚1.5至2.5米，重约360吨，形态奇特，如此巨石却被竖立在一块长约12－15米，" +
                        "宽8－10米的平坦岩石上，令人惊叹不已！两大岩石之间的接触面很小，上一石似从天外飞来，故名“仙云石”。" +
                        "地质学家认为，仙云石这一奇观是地质变化过程中形成的，真可谓天设地造。",78,"仙云石.jpg","有","有"),
                new ScenicSpot("一线天","一线天，存在在九曲溪二曲南面的一个幽邃的峡谷中。" +
                        "里面一座巍然挺立的巨石，从伏羲洞而入岩内，到了深处，抬头仰望，但见岩顶裂开一罅，" +
                        "就像是利斧劈开一样，相去不满一尺，长约一百多米，从中漏进天光一线，宛如跨空碧虹，" +
                        "这就是令人叹为观止的一线天。",56,"一线天.jpg","有","无"),
                new ScenicSpot("飞流瀑","古人形容“透陇隙南顾，则路左一溪悬捣，万练飞空，溪上石如莲叶下覆，中剜三门，" +
                        "水由叶上漫顶而下，如鲛绡万幅，横罩门外，直下者不可以丈数计，捣珠崩玉，飞沫反涌，如烟雾腾空，" +
                        "势甚雄厉；所谓‘珠帘钩不卷，飞练挂遥峰’，俱不足以拟其壮也。”",34,"飞流瀑.jpg","有","有"),
                new ScenicSpot("仙武湖","仙武湖三面环山，面积约6.39平方千米，东西宽约2.8千米，南北长约3.2千米，" +
                        "绕湖一周近15千米。湖中被孤山、白堤、苏堤、杨公堤分隔，按面积大小分别为外西湖、西里湖、" +
                        "北里湖、小南湖及岳湖等五片水面，苏堤、白堤越过湖面，小瀛洲、湖心亭、阮公墩三个小岛鼎立于外西湖湖心，" +
                        "夕照山的雷峰塔与宝石山的保俶塔隔湖相映，由此形成了“一山、二塔、三岛、三堤、五湖”的基本格局。",7,"仙武湖.jpg","无","有"),
//                new ScenicSpot("九曲桥","九曲桥九曲十八弯，且每个弯曲的角度大小不一，有大于90度直角的，也有小于90度直角的。九曲桥如"+
//                        "的桥面为花岗石板，每一弯曲处一块石板上均雕刻一朵季节性花朵，如正月水仙、二月杏花、三月桃花……直到十二月腊梅；" +
//                        "并在九曲桥头尾的两块石板上各雕刻一朵荷花。在湖心亭茶楼门前的一段桥面，中间雕刻一朵荷花，四角则分别雕刻彩云。" +
//                        "池中汉白玉的荷花仙女雕塑亭亭玉立，含笑迎候来客。" ,24,"九曲桥.jpg","有","无"),
                new ScenicSpot("观云台","坐观景索道到日月坪，站在山腰上一块空出的地方，就是观云台，站在观云台上可以看到西岭山风貌。" +
                        "观云台观变幻万千的云海奇景，特别是在天气明朗的日子，更是看云海的最佳地理位置， 翻滚的云海，波涛凶涌，" +
                        "仿佛置身天外仙境，有诗云：千仞高台谁筑成，登临一望客心惊；眼前波浪兼天涌，倒海翻江尽是云。",97,"观云台.jpg","有","无")
        };
        Edge[] edges={
                new Edge("北门","仙云石",8),
                new Edge("北门","狮子山",9),
                new Edge("狮子山","飞流瀑",6),
                new Edge("狮子山","一线天",7),
                new Edge("一线天","观云台",11),
                new Edge("飞流瀑","观云台",3),
//                new Edge("仙云石","九曲桥",5),
                new Edge("仙云石","仙武湖",4)
//                new Edge("仙武湖","九曲桥",7)
        };
     //连接数据库
    Class.forName(driver);
    String dbUrl = protocol + dbName + ";create=true";//新建数据库
    Connection connection = DriverManager.getConnection(dbUrl);
    Statement statement = connection.createStatement();
    //新建Edge表
    String   tableName="Edge";
    String tableDescription =  String.format
                ("CREATE TABLE %s" +
                        "(firstnode VARCHAR(50) NOT NULL,secondnode VARCHAR(50) NOT NULL,distance INT," +
                        "primary key(firstnode,secondnode))",tableName);
    statement.execute(tableDescription);
    //新建ScenicSpot表
    tableName="ScenicSpot";
    tableDescription =  String.format
          ("CREATE TABLE %s" +
              "(name VARCHAR(50) NOT NULL,introduce VARCHAR(500),popularity INT,picture VARCHAR(50),Toilet VARCHAR(50),Restarea VARCHAR(50)," +
                "primary key(name))",tableName);
    statement.execute(tableDescription);

    //插入数据
    tableName="ScenicSpot";
    String template=String.format("INSERT INTO %s VALUES(?, ?, ?, ?, ?, ?)",tableName);
    PreparedStatement inserter = connection.prepareStatement(template);
      for(ScenicSpot a: scenicSpots) {
        inserter.setString(1, a.getName());
        inserter.setString(2,a.getIntroduce());
        inserter.setInt(3, a.getPopularity());
        inserter.setString(4, a.getPicture());
        inserter.setString(5, a.getToilet());
        inserter.setString(6, a.getRestarea());
        inserter.executeUpdate();  
      }
        //插入数据
      tableName="Edge";
      template=String.format("INSERT INTO %s VALUES(?, ?, ?)",tableName);
      inserter = connection.prepareStatement(template);
      for(Edge t: edges) {
        inserter.setString(1, t.getFirstnode());
        inserter.setString(2, t.getSecondnode());
        inserter.setInt(3,t.getDistance());
        inserter.executeUpdate();
      }
      //关闭连接，建表完成
      inserter.close();
      connection.close();
    }
}
