package de.scravy;

public final class SourcePos {

  private String documentId;
  private int line;
  private int column;

  public SourcePos(final String documentId, final int line, final int column) {
    this.documentId = documentId;
    this.line = line;
    this.column = column;
  }

  public String getDocumentId() {
    return this.documentId;
  }

  public int getLine() {
    return this.line;
  }

  public int getColumn() {
    return this.column;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + column;
    result = prime * result + ((documentId == null) ? 0 : documentId.hashCode());
    result = prime * result + line;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    SourcePos other = (SourcePos) obj;
    if (column != other.column)
      return false;
    if (documentId == null) {
      if (other.documentId != null)
        return false;
    } else if (!documentId.equals(other.documentId))
      return false;
    if (line != other.line)
      return false;
    return true;
  }
}
