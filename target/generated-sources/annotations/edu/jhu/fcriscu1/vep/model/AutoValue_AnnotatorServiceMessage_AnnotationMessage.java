
package edu.jhu.fcriscu1.vep.model;

import edu.jhu.fcriscu1.vep.model.json.Annotation;
import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
final class AutoValue_AnnotatorServiceMessage_AnnotationMessage extends AnnotatorServiceMessage.AnnotationMessage {

  private final String hgvsVariation;
  private final String isoformId;
  private final Annotation vepAnnotation;

  AutoValue_AnnotatorServiceMessage_AnnotationMessage(
      String hgvsVariation,
      @javax.annotation.Nullable String isoformId,
      @javax.annotation.Nullable Annotation vepAnnotation) {
    if (hgvsVariation == null) {
      throw new NullPointerException("Null hgvsVariation");
    }
    this.hgvsVariation = hgvsVariation;
    this.isoformId = isoformId;
    this.vepAnnotation = vepAnnotation;
  }

  @Override
  public String hgvsVariation() {
    return hgvsVariation;
  }

  @javax.annotation.Nullable
  @Override
  public String isoformId() {
    return isoformId;
  }

  @javax.annotation.Nullable
  @Override
  public Annotation vepAnnotation() {
    return vepAnnotation;
  }

  @Override
  public String toString() {
    return "AnnotationMessage{"
        + "hgvsVariation=" + hgvsVariation + ", "
        + "isoformId=" + isoformId + ", "
        + "vepAnnotation=" + vepAnnotation
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof AnnotatorServiceMessage.AnnotationMessage) {
      AnnotatorServiceMessage.AnnotationMessage that = (AnnotatorServiceMessage.AnnotationMessage) o;
      return (this.hgvsVariation.equals(that.hgvsVariation()))
           && ((this.isoformId == null) ? (that.isoformId() == null) : this.isoformId.equals(that.isoformId()))
           && ((this.vepAnnotation == null) ? (that.vepAnnotation() == null) : this.vepAnnotation.equals(that.vepAnnotation()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= hgvsVariation.hashCode();
    h *= 1000003;
    h ^= (isoformId == null) ? 0 : isoformId.hashCode();
    h *= 1000003;
    h ^= (vepAnnotation == null) ? 0 : vepAnnotation.hashCode();
    return h;
  }

}
