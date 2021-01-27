export class recipes{

  id: number;
  title: string;
  ingredients: string[];
  href: string;
  thumbnail: string;

  constructor(id: number, title: string, ingredients: string[], href: string, thumbnail: string ){
      this.id = id;
      this.title = title;
      this.ingredients = ingredients;
      this.href = href;
      this.thumbnail = thumbnail;
  }
}
