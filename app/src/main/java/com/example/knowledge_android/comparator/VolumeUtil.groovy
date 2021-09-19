//package com.example.knowledge_android.comparator
//
//import javax.sound.sampled.AudioSystem
//import javax.sound.sampled.FloatControl
//import javax.sound.sampled.Mixer
//import javax.sound.sampled.Port
//
//class VolumeUtil {
//
//
//    public static void main(String[] args) {
//        println getVolumnValue()
//    }
//
//    /**
//     * 获取声音大小(0~10)
//     * @return
//     */
//    static int getVolumnValue() {
//        int storageVolume = 0
//        Mixer.Info[] infos = AudioSystem.getMixerInfo()
//        for (Mixer.Info info : infos) {
//            Mixer mixer = AudioSystem.getMixer(info)
//            if (mixer.isLineSupported(Port.Info.SPEAKER)) {
//                Port port = (Port) mixer.getLine(Port.Info.SPEAKER)
//                port.open()
//                if (port.isControlSupported(FloatControl.Type.VOLUME)) {
//                    FloatControl volume = (FloatControl) port.getControl(FloatControl.Type.VOLUME)
//                    System.out.println(info)
//                    System.out.println("- " + Port.Info.SPEAKER)
//                    System.out.println("  - " + volume)
//                    storageVolume = (volume.getValue() * 10).intValue()
//
////                    storageVolume = (float) Math.pow(10f, volume.getValue() / 20f);
//                }
//                port.close()
//            }
//        }
//        storageVolume
//    }
//
//    static void setVolumnValue(int value) {
//        Mixer.Info[] infos = AudioSystem.getMixerInfo()
//        for (Mixer.Info info : infos) {
//            Mixer mixer = AudioSystem.getMixer(info)
//            if (mixer.isLineSupported(Port.Info.SPEAKER)) {
//                Port port = (Port) mixer.getLine(Port.Info.SPEAKER)
//                port.open()
//                if (port.isControlSupported(FloatControl.Type.VOLUME)) {
//                    FloatControl volume = (FloatControl) port.getControl(FloatControl.Type.VOLUME)
//                    System.out.println(info)
//                    System.out.println("- " + Port.Info.SPEAKER)
//                    System.out.println("  - " + volume)
//                    volume.setValue(value / 10)
////                    volume.setValue(20f * (float) Math.log10(value / 10));
//                }
//                port.close()
//            }
//        }
//    }
//}
