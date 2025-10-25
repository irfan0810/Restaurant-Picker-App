export class Session {
  id: string;
  link: string;
  name: string;
  description: string;
  imageUrl: string;
  active: boolean;
  dateCreated: Date;
  lastUpdated: Date;
  result: string;

  constructor(
    id?: string,
    link?: string,
    name?: string,
    description?: string,
    imageUrl?: string,
    active?: boolean,
    dateCreated?: Date,
    lastUpdated?: Date,
    result?: string
  ) {
    this.id = id;
    this.link = link;
    this.name = name;
    this.description = description;
    this.imageUrl = imageUrl;
    this.active = active;
    this.dateCreated = dateCreated;
    this.lastUpdated = lastUpdated;
    this.result = result;
  }
}
