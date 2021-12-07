package com.ingenioussys.microfinance.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "manage_survey")

public class Survey {
    @PrimaryKey(autoGenerate = true)
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @ColumnInfo(name = "survey_title")
    String survey_title;

    public String getSurvey_title() {
        return survey_title;
    }

    public void setSurvey_title(String survey_title) {
        this.survey_title = survey_title;
    }

    @ColumnInfo(name = "survey_id")
    int survey_id;

    @ColumnInfo(name = "survey_uniqe_id")
    String survey_uniqe_id;

    public String getSurvey_uniqe_id() {
        return survey_uniqe_id;
    }

    public void setSurvey_uniqe_id(String survey_uniqe_id) {
        this.survey_uniqe_id = survey_uniqe_id;
    }

    public int getSurvey_id() {
        return survey_id;
    }

    public void setSurvey_id(int survey_id) {
        this.survey_id = survey_id;
    }

    @ColumnInfo(name = "bank_id")
    int bank_id;

    public int getBank_id() {
        return bank_id;
    }

    public void setBank_id(int bank_id) {
        this.bank_id = bank_id;
    }

    @ColumnInfo(name = "branch_id")
        int branch_id;

        @ColumnInfo(name = "area_id")
        int area_id;

        @ColumnInfo(name = "total_household")
        String total_household;

        @ColumnInfo(name = "minority_household")
        String minority_household;
        @ColumnInfo(name = "sc_household")
        String sc_household;
        @ColumnInfo(name = "obc_household")
        String obc_household;

        @ColumnInfo(name = "st_household")
        String st_household;

        @ColumnInfo(name = "area_acres")
        String area_acres;

        @ColumnInfo(name = "water_reservolr")
        String water_reservolr;

        @ColumnInfo(name = "rain_fed")
        String rain_fed;

        @ColumnInfo(name = "forest_land")
        String forest_land;

        @ColumnInfo(name = "irrigated_area")
        String irrigated_area;

        @ColumnInfo(name = "crop_name")
        String 	crop_name;

        @ColumnInfo(name = "under_cultivation")
        String 	under_cultivation;

        @ColumnInfo(name = "per_acre_yield")
        String 	per_acre_yield;

        @ColumnInfo(name = "crop_name_second")
        String 	crop_name_second;

        @ColumnInfo(name = "per_acre_yield_second")
        String 	per_acre_yield_second;

        @ColumnInfo(name = "crop_name_third")
        String 	crop_name_third;

        @ColumnInfo(name = "under_cultivation_third")
        String 	under_cultivation_third;

        @ColumnInfo(name = "per_acre_yield_third")
        String 	per_acre_yield_third;

        @ColumnInfo(name = "crop_name_fourth")
        String 	crop_name_fourth;

        @ColumnInfo(name = "under_cultivation_fourth")
        String 	under_cultivation_fourth;
        @ColumnInfo(name = "per_acre_yield_fourth")
        String 	per_acre_yield_fourth;

        @ColumnInfo(name = "number_of_hotels")
        String 	number_of_hotels;

        @ColumnInfo(name = "number_of_utensil_shops")
        String 	number_of_utensil_shops;

        @ColumnInfo(name = "number_of_tea_shops")
        String 	number_of_tea_shops;

        @ColumnInfo(name = "number_of_agri_shops")
        String 	number_of_agri_shops;

        @ColumnInfo(name = "number_of_kirana_shops")
        String 	number_of_kirana_shops;

        @ColumnInfo(name = "number_of_agree_proce_unit")
        String 	number_of_agree_proce_unit;

        @ColumnInfo(name = "number_of_tailoring_shops")
        String 	number_of_tailoring_shops;

        @ColumnInfo(name = "number_of_pan_shops")
        String 	number_of_pan_shops;

        @ColumnInfo(name = "number_of_cycle_shops")
        String 	number_of_cycle_shops;

        @ColumnInfo(name = "number_of_other_shops")
        String 	number_of_other_shops;

        @ColumnInfo(name = "number_of_repair_service_shops")
        String 	number_of_repair_service_shops;

        @ColumnInfo(name = "number_of_school")
        String number_of_school;

        @ColumnInfo(name = "dairy_society_number")
        String dairy_society_number;

        @ColumnInfo(name = "dairy_society_client")
        String dairy_society_client;

        @ColumnInfo(name = "farmers_club_number")
        String farmers_club_number;

        @ColumnInfo(name = "farmers_club_client")
        String farmers_club_client;

        @ColumnInfo(name = "shgs_number")
        String shgs_number;

        @ColumnInfo(name = "shgs_client")
        String shgs_client;

        @ColumnInfo(name = "co_operative_number")
        String co_operative_number;

        @ColumnInfo(name = "co_operative_client")
        String 	co_operative_client;

        @ColumnInfo(name = "co_operative_branch_number")
        String 	co_operative_branch_number;

        @ColumnInfo(name = "mfi_client_1")
        String 	mfi_client_1;

        @ColumnInfo(name = "mfi_number_1")
        String 	mfi_number_1;

        @ColumnInfo(name = "mfi_name_1")
        String 	mfi_name_1;

        @ColumnInfo(name = "mfi_name_2")
        String 	mfi_name_2;

        @ColumnInfo(name = "mfi_number_2")
        String 	mfi_number_2;

        @ColumnInfo(name = "mfi_client_2")
        String 	mfi_client_2;

        @ColumnInfo(name = "mfi_name_3")
        String 	mfi_name_3;

        @ColumnInfo(name = "mfi_number_3")
        String 	mfi_number_3;

        @ColumnInfo(name = "mfi_client_3")
        String 	mfi_client_3;

        @ColumnInfo(name = "mfi_name_4")
        String 	mfi_name_4;

        @ColumnInfo(name = "mfi_number_4")
        String 	mfi_number_4;

        @ColumnInfo(name = "mfi_client_4")
        String 	mfi_client_4;

        @ColumnInfo(name = "survey_created")
        String 	survey_created;

        @ColumnInfo(name = "survey_created_by")
        int 	survey_created_by;

        @ColumnInfo(name = "survey_lat")
        double 	survey_lat;

        @ColumnInfo(name = "survey_long")
        double 	survey_long;

        @ColumnInfo(name = "mbb_name")
        String 	mbb_name;

        @ColumnInfo(name = "under_cultivation_second")
        String 	under_cultivation_second;

        @ColumnInfo(name = "co_operative_branch_client")
        String 	co_operative_branch_client;

        @ColumnInfo(name = "grameem_branch_number")
        String 	grameem_branch_number;

        @ColumnInfo(name = "grameem_branch_client")
        String 	grameem_branch_client;

        @ColumnInfo(name = "bus_service")
        String 	bus_service;

        @ColumnInfo(name = "auto_service")
        String 	auto_service;

        @ColumnInfo(name = "police_station")
        String 	police_station;

        @ColumnInfo(name = "market")
        String 	market;

    @ColumnInfo(name = "primary_health_center")
    String 	primary_health_center;

    @ColumnInfo(name = "primary_school")
    String 		primary_school;

    @ColumnInfo(name = "suvery_verfied_by")
    int suvery_verfied_by;

    @ColumnInfo(name = "surver_verfication_status")
    int surver_verfication_status;

    @ColumnInfo(name = "survey_area_name")
    String survey_area_name;

    public int getSuvery_verfied_by() {
        return suvery_verfied_by;
    }

    public String getSurvey_area_name() {
        return survey_area_name;
    }

    public void setSurvey_area_name(String survey_area_name) {
        this.survey_area_name = survey_area_name;
    }

    public void setSuvery_verfied_by(int suvery_verfied_by) {
        this.suvery_verfied_by = suvery_verfied_by;
    }

    public int getSurver_verfication_status() {
        return surver_verfication_status;
    }

    public void setSurver_verfication_status(int surver_verfication_status) {
        this.surver_verfication_status = surver_verfication_status;
    }

    public String getBus_service() {
        return bus_service;
    }

    public void setBus_service(String bus_service) {
        this.bus_service = bus_service;
    }

    public String getAuto_service() {
        return auto_service;
    }

    public void setAuto_service(String auto_service) {
        this.auto_service = auto_service;
    }

    public String getPolice_station() {
        return police_station;
    }

    public void setPolice_station(String police_station) {
        this.police_station = police_station;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getPrimary_health_center() {
        return primary_health_center;
    }

    public void setPrimary_health_center(String primary_health_center) {
        this.primary_health_center = primary_health_center;
    }

    public String getPrimary_school() {
        return primary_school;
    }

    public void setPrimary_school(String primary_school) {
        this.primary_school = primary_school;
    }

    public String getQuality_of_roads() {
        return quality_of_roads;
    }

    public void setQuality_of_roads(String quality_of_roads) {
        this.quality_of_roads = quality_of_roads;
    }

    @ColumnInfo(name = "quality_of_roads")
    String quality_of_roads;

    public String getGrameem_branch_number() {
        return grameem_branch_number;
    }

    public void setGrameem_branch_number(String grameem_branch_number) {
        this.grameem_branch_number = grameem_branch_number;
    }

    public String getGrameem_branch_client() {
        return grameem_branch_client;
    }

    public void setGrameem_branch_client(String grameem_branch_client) {
        this.grameem_branch_client = grameem_branch_client;
    }

    public String getComercial_branch_client() {
        return comercial_branch_client;
    }

    public void setComercial_branch_client(String comercial_branch_client) {
        this.comercial_branch_client = comercial_branch_client;
    }

    public String getComercial_branch_number() {
        return comercial_branch_number;
    }

    public void setComercial_branch_number(String comercial_branch_number) {
        this.comercial_branch_number = comercial_branch_number;
    }

    @ColumnInfo(name = "comercial_branch_client")
        String 	comercial_branch_client;

        @ColumnInfo(name = "comercial_branch_number")
        String 	comercial_branch_number;

    public String getCo_operative_branch_client() {
        return co_operative_branch_client;
    }

    public void setCo_operative_branch_client(String co_operative_branch_client) {
        this.co_operative_branch_client = co_operative_branch_client;
    }

    public String getUnder_cultivation_second() {
        return under_cultivation_second;
    }

    public void setUnder_cultivation_second(String under_cultivation_second) {
        this.under_cultivation_second = under_cultivation_second;
    }

    public String getMbb_name() {
        return mbb_name;
    }

    public void setMbb_name(String mbb_name) {
        this.mbb_name = mbb_name;
    }



        public int getBranch_id() {
            return branch_id;
        }

        public void setBranch_id(int branch_id) {
            this.branch_id = branch_id;
        }

        public int getArea_id() {
            return area_id;
        }

        public void setArea_id(int area_id) {
            this.area_id = area_id;
        }

        public String getTotal_household() {
            return total_household;
        }

        public void setTotal_household(String total_household) {
            this.total_household = total_household;
        }

        public String getMinority_household() {
            return minority_household;
        }

        public void setMinority_household(String minority_household) {
            this.minority_household = minority_household;
        }

        public String getSc_household() {
            return sc_household;
        }

        public void setSc_household(String sc_household) {
            this.sc_household = sc_household;
        }

        public String getObc_household() {
            return obc_household;
        }

        public void setObc_household(String obc_household) {
            this.obc_household = obc_household;
        }

        public String getSt_household() {
            return st_household;
        }

        public void setSt_household(String st_household) {
            this.st_household = st_household;
        }

        public String getArea_acres() {
            return area_acres;
        }

        public void setArea_acres(String area_acres) {
            this.area_acres = area_acres;
        }

        public String getWater_reservolr() {
            return water_reservolr;
        }

        public void setWater_reservolr(String water_reservolr) {
            this.water_reservolr = water_reservolr;
        }

        public String getRain_fed() {
            return rain_fed;
        }

        public void setRain_fed(String rain_fed) {
            this.rain_fed = rain_fed;
        }

        public String getForest_land() {
            return forest_land;
        }

        public void setForest_land(String forest_land) {
            this.forest_land = forest_land;
        }

        public String getIrrigated_area() {
            return irrigated_area;
        }

        public void setIrrigated_area(String irrigated_area) {
            this.irrigated_area = irrigated_area;
        }

        public String getCrop_name() {
            return crop_name;
        }

        public void setCrop_name(String crop_name) {
            this.crop_name = crop_name;
        }

        public String getUnder_cultivation() {
            return under_cultivation;
        }

        public void setUnder_cultivation(String under_cultivation) {
            this.under_cultivation = under_cultivation;
        }

        public String getPer_acre_yield() {
            return per_acre_yield;
        }

        public void setPer_acre_yield(String per_acre_yield) {
            this.per_acre_yield = per_acre_yield;
        }

        public String getCrop_name_second() {
            return crop_name_second;
        }

        public void setCrop_name_second(String crop_name_second) {
            this.crop_name_second = crop_name_second;
        }

        public String getPer_acre_yield_second() {
            return per_acre_yield_second;
        }

        public void setPer_acre_yield_second(String per_acre_yield_second) {
            this.per_acre_yield_second = per_acre_yield_second;
        }

        public String getCrop_name_third() {
            return crop_name_third;
        }

        public void setCrop_name_third(String crop_name_third) {
            this.crop_name_third = crop_name_third;
        }

        public String getUnder_cultivation_third() {
            return under_cultivation_third;
        }

        public void setUnder_cultivation_third(String under_cultivation_third) {
            this.under_cultivation_third = under_cultivation_third;
        }

        public String getPer_acre_yield_third() {
            return per_acre_yield_third;
        }

        public void setPer_acre_yield_third(String per_acre_yield_third) {
            this.per_acre_yield_third = per_acre_yield_third;
        }

        public String getCrop_name_fourth() {
            return crop_name_fourth;
        }

        public void setCrop_name_fourth(String crop_name_fourth) {
            this.crop_name_fourth = crop_name_fourth;
        }

        public String getUnder_cultivation_fourth() {
            return under_cultivation_fourth;
        }

        public void setUnder_cultivation_fourth(String under_cultivation_fourth) {
            this.under_cultivation_fourth = under_cultivation_fourth;
        }

        public String getPer_acre_yield_fourth() {
            return per_acre_yield_fourth;
        }

        public void setPer_acre_yield_fourth(String per_acre_yield_fourth) {
            this.per_acre_yield_fourth = per_acre_yield_fourth;
        }

        public String getNumber_of_hotels() {
            return number_of_hotels;
        }

        public void setNumber_of_hotels(String number_of_hotels) {
            this.number_of_hotels = number_of_hotels;
        }

        public String getNumber_of_utensil_shops() {
            return number_of_utensil_shops;
        }

        public void setNumber_of_utensil_shops(String number_of_utensil_shops) {
            this.number_of_utensil_shops = number_of_utensil_shops;
        }

        public String getNumber_of_tea_shops() {
            return number_of_tea_shops;
        }

        public void setNumber_of_tea_shops(String number_of_tea_shops) {
            this.number_of_tea_shops = number_of_tea_shops;
        }

        public String getNumber_of_agri_shops() {
            return number_of_agri_shops;
        }

        public void setNumber_of_agri_shops(String number_of_agri_shops) {
            this.number_of_agri_shops = number_of_agri_shops;
        }

        public String getNumber_of_kirana_shops() {
            return number_of_kirana_shops;
        }

        public void setNumber_of_kirana_shops(String number_of_kirana_shops) {
            this.number_of_kirana_shops = number_of_kirana_shops;
        }

        public String getNumber_of_agree_proce_unit() {
            return number_of_agree_proce_unit;
        }

        public void setNumber_of_agree_proce_unit(String number_of_agree_proce_unit) {
            this.number_of_agree_proce_unit = number_of_agree_proce_unit;
        }

        public String getNumber_of_tailoring_shops() {
            return number_of_tailoring_shops;
        }

        public void setNumber_of_tailoring_shops(String number_of_tailoring_shops) {
            this.number_of_tailoring_shops = number_of_tailoring_shops;
        }

        public String getNumber_of_pan_shops() {
            return number_of_pan_shops;
        }

        public void setNumber_of_pan_shops(String number_of_pan_shops) {
            this.number_of_pan_shops = number_of_pan_shops;
        }

        public String getNumber_of_cycle_shops() {
            return number_of_cycle_shops;
        }

        public void setNumber_of_cycle_shops(String number_of_cycle_shops) {
            this.number_of_cycle_shops = number_of_cycle_shops;
        }

        public String getNumber_of_other_shops() {
            return number_of_other_shops;
        }

        public void setNumber_of_other_shops(String number_of_other_shops) {
            this.number_of_other_shops = number_of_other_shops;
        }

        public String getNumber_of_repair_service_shops() {
            return number_of_repair_service_shops;
        }

        public void setNumber_of_repair_service_shops(String number_of_repair_service_shops) {
            this.number_of_repair_service_shops = number_of_repair_service_shops;
        }

        public String getNumber_of_school() {
            return number_of_school;
        }

        public void setNumber_of_school(String number_of_school) {
            this.number_of_school = number_of_school;
        }

        public String getDairy_society_number() {
            return dairy_society_number;
        }

        public void setDairy_society_number(String dairy_society_number) {
            this.dairy_society_number = dairy_society_number;
        }

        public String getDairy_society_client() {
            return dairy_society_client;
        }

        public void setDairy_society_client(String dairy_society_client) {
            this.dairy_society_client = dairy_society_client;
        }

        public String getFarmers_club_number() {
            return farmers_club_number;
        }

        public void setFarmers_club_number(String farmers_club_number) {
            this.farmers_club_number = farmers_club_number;
        }

        public String getFarmers_club_client() {
            return farmers_club_client;
        }

        public void setFarmers_club_client(String farmers_club_client) {
            this.farmers_club_client = farmers_club_client;
        }

        public String getShgs_number() {
            return shgs_number;
        }

        public void setShgs_number(String shgs_number) {
            this.shgs_number = shgs_number;
        }

        public String getShgs_client() {
            return shgs_client;
        }

        public void setShgs_client(String shgs_client) {
            this.shgs_client = shgs_client;
        }

        public String getCo_operative_number() {
            return co_operative_number;
        }

        public void setCo_operative_number(String co_operative_number) {
            this.co_operative_number = co_operative_number;
        }

        public String getCo_operative_client() {
            return co_operative_client;
        }

        public void setCo_operative_client(String co_operative_client) {
            this.co_operative_client = co_operative_client;
        }

        public String getCo_operative_branch_number() {
            return co_operative_branch_number;
        }

        public void setCo_operative_branch_number(String co_operative_branch_number) {
            this.co_operative_branch_number = co_operative_branch_number;
        }

        public String getMfi_client_1() {
            return mfi_client_1;
        }

        public void setMfi_client_1(String mfi_client_1) {
            this.mfi_client_1 = mfi_client_1;
        }

        public String getMfi_number_1() {
            return mfi_number_1;
        }

        public void setMfi_number_1(String mfi_number_1) {
            this.mfi_number_1 = mfi_number_1;
        }

        public String getMfi_name_1() {
            return mfi_name_1;
        }

        public void setMfi_name_1(String mfi_name_1) {
            this.mfi_name_1 = mfi_name_1;
        }

        public String getMfi_name_2() {
            return mfi_name_2;
        }

        public void setMfi_name_2(String mfi_name_2) {
            this.mfi_name_2 = mfi_name_2;
        }

        public String getMfi_number_2() {
            return mfi_number_2;
        }

        public void setMfi_number_2(String mfi_number_2) {
            this.mfi_number_2 = mfi_number_2;
        }

        public String getMfi_client_2() {
            return mfi_client_2;
        }

        public void setMfi_client_2(String mfi_client_2) {
            this.mfi_client_2 = mfi_client_2;
        }

        public String getMfi_name_3() {
            return mfi_name_3;
        }

        public void setMfi_name_3(String mfi_name_3) {
            this.mfi_name_3 = mfi_name_3;
        }

        public String getMfi_number_3() {
            return mfi_number_3;
        }

        public void setMfi_number_3(String mfi_number_3) {
            this.mfi_number_3 = mfi_number_3;
        }

        public String getMfi_client_3() {
            return mfi_client_3;
        }

        public void setMfi_client_3(String mfi_client_3) {
            this.mfi_client_3 = mfi_client_3;
        }

        public String getMfi_name_4() {
            return mfi_name_4;
        }

        public void setMfi_name_4(String mfi_name_4) {
            this.mfi_name_4 = mfi_name_4;
        }

        public String getMfi_number_4() {
            return mfi_number_4;
        }

        public void setMfi_number_4(String mfi_number_4) {
            this.mfi_number_4 = mfi_number_4;
        }

        public String getMfi_client_4() {
            return mfi_client_4;
        }

        public void setMfi_client_4(String mfi_client_4) {
            this.mfi_client_4 = mfi_client_4;
        }

        public String getSurvey_created() {
            return survey_created;
        }

        public void setSurvey_created(String survey_created) {
            this.survey_created = survey_created;
        }

        public int getSurvey_created_by() {
            return survey_created_by;
        }

        public void setSurvey_created_by(int survey_created_by) {
            this.survey_created_by = survey_created_by;
        }

        public double getSurvey_lat() {
            return survey_lat;
        }

        public void setSurvey_lat(double survey_lat) {
            this.survey_lat = survey_lat;
        }

        public double getSurvey_long() {
            return survey_long;
        }

        public void setSurvey_long(double survey_long) {
            this.survey_long = survey_long;
        }

}
