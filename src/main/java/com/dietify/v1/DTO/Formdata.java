package com.dietify.v1.DTO;

public class Formdata {
    
    
        private String timeFrame;
        private int targetCalories;
        private String diet;
        private String exclude;
    
        // Getter and setter for timeFrame
        public String getTimeFrame() {
            return timeFrame;
        }
    
        public void setTimeFrame(String timeFrame) {
            this.timeFrame = timeFrame;
        }
    
        // Getter and setter for targetCalories
        public int getTargetCalories() {
            return targetCalories;
        }
    
        public void setTargetCalories(int targetCalories) {
            this.targetCalories = targetCalories;
        }
    
        // Getter and setter for diet
        public String getDiet() {
            return diet;
        }
    
        public void setDiet(String diet) {
            this.diet = diet;
        }
    
        // Getter and setter for exclude
        public String getExclude() {
            return exclude;
        }
    
        public void setExclude(String exclude) {
            this.exclude = exclude;
        }
    
    
}