package de.skat3.ai;

/**
 * 
 * 
 * @author Aljoscha Domonell
 *
 */
public enum AiNames {
  Aljoscha(true), Kai(true), Jonas(false), Timo(false), Artem(true), Emre(true), Hildegard(
      true), Gertrud(true), Chantal(false), Justin(false), Kevin(
          false), Albert(true), Jacqueline(true), Herbert(false), Angie(false), Siri(false);

  private boolean hard;
  private boolean used;

  private AiNames(boolean hard) {
    this.hard = hard;
    this.used = false;
  }

  public static String getRandomName(boolean hard) {
    AiNames person = null;
    int iteration = 0;
    while (true) {
      int index = (int) (Math.random() * AiNames.values().length);

      if (AiNames.values()[index].hard == hard && AiNames.values()[index].used == false) {
        AiNames.values()[index].used = true;
        person = AiNames.values()[index];
        return person.name();

      } else {

        if (iteration < AiNames.values().length * 3) {
          iteration++;

        } else {

          for (int i = 0; i < AiNames.values().length; i++) {
            if (AiNames.values()[i].hard == hard && AiNames.values()[i].used == false) {
              AiNames.values()[i].used = true;
              person = AiNames.values()[i];
              return person.name();
            }
          }
          for (int i = 0; i < AiNames.values().length; i++) {
            if (AiNames.values()[i].hard == hard) {
              AiNames.values()[i].used = false;
            }
          }
          iteration = 0;

        }
      }
    }
  }

  public static void resetUsedValues() {
    for (int i = 0; i < AiNames.values().length; i++) {
      AiNames.values()[i].used = false;
    }
  }
}
