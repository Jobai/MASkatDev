package de.skat3.ai;

/**
 * 
 * @author Aljoscha Domonell
 *
 */
public enum AiNames {
  Aljoscha(true), Kai(true), Jonas(false), Timo(false), Artem(true), Emre(true), Hildegard(
      true), Gertrud(true), Chantal(
          false), Justin(false), Assitoni(false), Kevin(false), Albert(true), Jacqueline(true);

  private boolean hard;

  private AiNames(boolean hard) {
    this.hard = hard;
  }

  public static String getRandomName(boolean hardName) {
    AiNames person = null;
    while (person == null) {
      int index = (int) Math.random() * AiNames.values().length;
      if (AiNames.values()[index].hard == hardName) {
        person = AiNames.values()[index];
      }
    }

    return person.name();
  }
}
